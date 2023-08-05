package sorsix.project.courseify.domain.response

data class UserResponse(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val username: String
)
