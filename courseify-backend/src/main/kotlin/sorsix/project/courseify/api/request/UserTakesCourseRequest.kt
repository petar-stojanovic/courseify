package sorsix.project.courseify.api.request

data class UserTakesCourseRequest(
    val userId: Long,
    val courseId: Long
) {
}
