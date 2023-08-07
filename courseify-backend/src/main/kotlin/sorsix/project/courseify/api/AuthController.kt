package sorsix.project.courseify.api

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sorsix.project.courseify.api.request.AuthenticationRequest
import sorsix.project.courseify.api.request.RegisterRequest
import sorsix.project.courseify.api.request.ResetPasswordRequest
import sorsix.project.courseify.domain.response.AuthenticationResponse
import sorsix.project.courseify.domain.response.UserResponse
import sorsix.project.courseify.repository.UserRepository
import sorsix.project.courseify.service.definitions.AuthService


@RestController
@RequestMapping("/api/auth")
class AuthController(val authService: AuthService, val userRepository: UserRepository) {


    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<AuthenticationResponse> =
        ResponseEntity.ok(authService.register(request))

    @PostMapping("/authenticate")
    fun authenticate(
        @RequestBody request: AuthenticationRequest
    ): ResponseEntity<*> {
        return authService.authenticate(request)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed")
    }

    @PostMapping("/change-password")
    fun resetPassword(
        @RequestBody request: ResetPasswordRequest
    ): ResponseEntity<UserResponse> {
        return authService.resetPassword(request).let {
            val user = UserResponse(it.id, it.email, it.firstName, it.lastName, it.username)
            ResponseEntity.ok(user)
        }
    }

    @PostMapping("/refresh-token")
    fun refreshToken(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        authService.refreshToken(request, response)
    }


    @PostMapping("/logout")
    fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        return authService.logout(request, response).let {
            ResponseEntity.ok(it)
        }
    }

    @GetMapping("/user/{token}")
    fun getUserByToken(@PathVariable token: String): UserResponse {
        val user = userRepository.findUserByToken(token)
        return UserResponse(user.id, user.email, user.firstName, user.lastName, user.username)
    }
}
