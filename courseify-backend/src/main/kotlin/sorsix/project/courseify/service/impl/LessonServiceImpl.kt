package sorsix.project.courseify.service.impl

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import sorsix.project.courseify.api.request.LessonRequest
import sorsix.project.courseify.domain.Lesson
import sorsix.project.courseify.repository.CourseRepository
import sorsix.project.courseify.repository.LessonRepository
import sorsix.project.courseify.repository.QuizRepository
import sorsix.project.courseify.service.LessonService
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
class LessonServiceImpl(
    val lessonRepository: LessonRepository,
    val courseRepository: CourseRepository,
    val quizRepository: QuizRepository
) : LessonService {

    private val path: Path = Paths.get("uploads")

    override fun upload(file: MultipartFile) {
        try {
            Files.copy(file.inputStream, path.resolve(file.originalFilename))
        } catch (e: Exception) {
            if (e is FileAlreadyExistsException) {
                throw RuntimeException("A file of that name already exists.")
            }
            throw RuntimeException(e.message)
        }
    }

    override fun save(request: LessonRequest) {

        val lessonTitleSlug = request.title.lowercase()
            .replace(" ", "_")

        val pathToUpload = Files.createDirectory(path.resolve(lessonTitleSlug))

        Files.copy(request.video.inputStream, pathToUpload.resolve("${request.videoTitle}.mp4"))
        Files.copy(request.file.inputStream, pathToUpload.resolve("${request.fileTitle}.pdf"))

        val copiedVideoPath = pathToUpload
            .resolve("${request.videoTitle}.mp4")
            .toAbsolutePath().toString()

        val copiedFilePath = pathToUpload
            .resolve("${request.fileTitle}.pdf")
            .toAbsolutePath().toString()

        val course = courseRepository.findById(request.courseId).get()
        val quiz = quizRepository.findById(request.quizId).get()
        lessonRepository.save(
            Lesson(
                0, request.title, request.description, request.videoTitle,
                copiedVideoPath, request.fileTitle, copiedFilePath, course, quiz
            )
        )
    }
}
