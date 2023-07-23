package sorsix.project.courseify.service.impl

import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
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
    override fun save(request: LessonRequest) {

        val course = courseRepository.findById(request.courseId).get()
        val root: Path = Paths.get(
            "uploads/${
                course.title.lowercase()
                    .replace(" ", "_")
            }"
        )
        val lessonTitleSlug = request.title.lowercase()
            .replace(" ", "_")

        val pathToUpload = Files.createDirectories(root.resolve(lessonTitleSlug))

        val videoExtension =
            request.video.originalFilename?.substring(request.video.originalFilename!!.lastIndexOf(".") + 1)
        val fileExtension =
            request.file.originalFilename?.substring(request.file.originalFilename!!.lastIndexOf(".") + 1)

        val fullVideoName = "${request.videoTitle}.$videoExtension"
        val fullFileName = "${request.fileTitle}.$fileExtension"

        Files.copy(request.video.inputStream, pathToUpload.resolve(fullVideoName))
        Files.copy(request.file.inputStream, pathToUpload.resolve(fullFileName))

        val copiedVideoPath = pathToUpload
            .resolve(fullVideoName)
            .toAbsolutePath().toString()

        val copiedFilePath = pathToUpload
            .resolve(fullFileName)
            .toAbsolutePath().toString()


        val quiz = quizRepository.findById(request.quizId).get()
        lessonRepository.save(
            Lesson(
                0, request.title, request.description, request.videoTitle,
                copiedVideoPath, request.fileTitle, copiedFilePath, course, quiz
            )
        )
    }

    override fun getLessonVideoData(videoTitle: String, courseId: Long, lessonId: Long): Resource? {
        val coursePath = courseRepository.findById(courseId).get().title.lowercase().replace(" ", "_")
        val lessonPath = lessonRepository.findById(lessonId).get().title.lowercase().replace(" ", "_")
        val videoPath = Paths.get("uploads/$coursePath/$lessonPath/$videoTitle.mkv")

        /** Moze da se najde ss celosnu pateku pocnuvajkji od uploads/
         * C:\Users\Petar\Desktop\courseify\courseify-backend\uploads\course_title\hello_world\testVideo.mkv
         * da se zeme uploads\course_title\hello_world\testVideo.mkv
         * takoj ne mora svi da bidev mp4
         * */
        return ByteArrayResource(Files.readAllBytes(videoPath))

    }
}
