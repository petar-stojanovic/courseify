package sorsix.project.courseify.domain.response

data class CourseResponse(
    val id: Long,
    val title: String,
    val description: String,
    val isActive: Boolean,
) {
}
