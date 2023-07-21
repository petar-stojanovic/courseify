package sorsix.project.courseify.api.request

import org.springframework.web.multipart.MultipartFile
import sorsix.project.courseify.domain.Course
import sorsix.project.courseify.domain.Quiz

data class LessonRequest(
    val title: String,
    val description: String,
    val videoTitle: String,
    val video: MultipartFile,
    val fileTitle: String,
    val file: MultipartFile,
    val courseId: Long,
    val quizId: Long
) {
}
