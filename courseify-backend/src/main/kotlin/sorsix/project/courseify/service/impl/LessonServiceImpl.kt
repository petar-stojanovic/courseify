package sorsix.project.courseify.service.impl

import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import sorsix.project.courseify.api.request.LessonRequest
import sorsix.project.courseify.domain.Lesson
import sorsix.project.courseify.repository.CourseRepository
import sorsix.project.courseify.repository.LessonRepository
import sorsix.project.courseify.repository.QuizRepository
import sorsix.project.courseify.service.definitions.LessonService
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
class LessonServiceImpl(
    val lessonRepository: LessonRepository,
    val courseRepository: CourseRepository,
    val quizRepository: QuizRepository
) : LessonService {

    override fun save(request: LessonRequest): Lesson {

        val course = courseRepository.findById(request.courseId).get()
        val root: Path = Paths.get(
            "uploads/${
                course.title.toSlug()
            }"
        )
        val lessonTitleSlug = request.title.toSlug()

        val pathToUpload = Files.createDirectories(root.resolve(lessonTitleSlug))

        val videoExtension =
            request.video.originalFilename?.substring(request.video.originalFilename!!.lastIndexOf(".") + 1)
        val fileExtension =
            request.file.originalFilename?.substring(request.file.originalFilename!!.lastIndexOf(".") + 1)

        val fullVideoName = "video.$videoExtension"
        val fullFileName = "file.$fileExtension"

        Files.copy(request.video.inputStream, pathToUpload.resolve(fullVideoName))
        Files.copy(request.file.inputStream, pathToUpload.resolve(fullFileName))

        val copiedVideoPath = pathToUpload
            .resolve(fullVideoName)
            .toAbsolutePath().toString()

        val copiedFilePath = pathToUpload
            .resolve(fullFileName)
            .toAbsolutePath().toString()


        val quiz = quizRepository.findByIdOrNull(request.quizId)

        return lessonRepository.save(
            Lesson(
                0, request.title, request.description, request.videoTitle,
                copiedVideoPath, request.fileTitle, copiedFilePath, course, quiz
            )
        )
    }


    override fun delete(id: Long) {
        val lesson = this.lessonRepository.findById(id).get()
        val lessonPath = lesson.title.toSlug()
        val coursePath = lesson.course.title.toSlug()
        File("uploads/$coursePath/$lessonPath").deleteRecursively()

        lessonRepository.delete(lesson)
    }

    override fun editLesson(id: Long, request: LessonRequest) = lessonRepository.findByIdOrNull(id)?.let {

        val course = courseRepository.findById(request.courseId).get()

        val oldLessonSlug = it.title.toSlug()
        val courseSlug = course.title.toSlug()
        val oldPath = Paths.get("uploads/$courseSlug/$oldLessonSlug")
        File(oldPath.toString()).deleteRecursively()

        val newLessonSlug = request.title.toSlug()

        val newPath = Files.createDirectories(Paths.get("uploads/$courseSlug/$newLessonSlug"))

        val videoExtension =
            request.video.originalFilename?.substring(request.video.originalFilename!!.lastIndexOf(".") + 1)
        val fileExtension =
            request.file.originalFilename?.substring(request.file.originalFilename!!.lastIndexOf(".") + 1)

        val fullVideoName = "video.$videoExtension"
        val fullFileName = "file.$fileExtension"

        Files.copy(request.video.inputStream, newPath.resolve(fullVideoName))
        Files.copy(request.file.inputStream, newPath.resolve(fullFileName))

        val copiedVideoPath = newPath
            .resolve(fullVideoName)
            .toAbsolutePath().toString()

        val copiedFilePath = newPath
            .resolve(fullFileName)
            .toAbsolutePath().toString()


        val quiz = quizRepository.findByIdOrNull(request.quizId)
        lessonRepository.save(
            Lesson(
                id, request.title, request.description, request.videoTitle,
                copiedVideoPath, request.fileTitle, copiedFilePath, course, quiz
            )
        )
    }

    override fun getFile(lessonId: Long): Resource? = getResource(lessonId, "file")

    override fun getVideo(lessonId: Long): Resource? = getResource(lessonId, "video")

    private fun getResource(lessonId: Long, resourceType: String): Resource? =
        lessonRepository.findByIdOrNull(lessonId)?.let {

            val coursePath = it.course.title.toSlug()
            val lessonPath = it.title.toSlug()

            val ext = if (resourceType == "video") {
                it.videoUrl
            } else {
                it.fileUrl
            }

            val extension = ext.substring(ext.lastIndexOf(".") + 1)
            val filePath = Paths.get("uploads/$coursePath/$lessonPath/$resourceType.$extension")

            return ByteArrayResource(Files.readAllBytes(filePath))
        }
}
