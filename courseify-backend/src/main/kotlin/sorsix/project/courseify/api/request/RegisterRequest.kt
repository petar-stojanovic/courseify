package sorsix.project.courseify.api.request

import sorsix.project.courseify.domain.Role

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val username: String,
    val role: Role,
) {}
