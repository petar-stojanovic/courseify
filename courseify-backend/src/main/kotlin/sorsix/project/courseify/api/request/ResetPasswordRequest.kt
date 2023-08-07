package sorsix.project.courseify.api.request

data class ResetPasswordRequest(
    val username: String,
    val oldPassword: String,
    val newPassword: String
)
