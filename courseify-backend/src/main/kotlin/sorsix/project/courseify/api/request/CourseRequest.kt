package sorsix.project.courseify.api.request

import org.springframework.web.multipart.MultipartFile

data class CourseRequest(
    val title: String,
    val thumbnail: MultipartFile,
    val description: String,
//    val authorId: Long,
    val categoryIds: String
) {
}
