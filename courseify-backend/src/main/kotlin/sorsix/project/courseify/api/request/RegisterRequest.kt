package sorsix.project.courseify.api.request

import jakarta.validation.constraints.NotBlank
import sorsix.project.courseify.api.validator.UsernameMatchConstraint
import sorsix.project.courseify.domain.Role

@UsernameMatchConstraint
data class RegisterRequest(
    val firstName: String,

    val lastName: String,

    val email: String,

    @get:NotBlank
    val password: String,

    val username: String,

    val confirmPassword: String,

    val role: Role,
) {}
