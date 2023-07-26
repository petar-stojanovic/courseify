package sorsix.project.courseify.api

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sorsix.project.courseify.api.request.AuthenticationRequest
import sorsix.project.courseify.api.request.RegisterRequest
import sorsix.project.courseify.domain.response.AuthenticationResponse
import sorsix.project.courseify.service.definitions.AuthService


@RestController
@RequestMapping("/api/auth")
class AuthController(val authService: AuthService) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<AuthenticationResponse> =
        ResponseEntity.ok(authService.register(request))

    @PostMapping("/authenticate")
    fun authenticate(
        @RequestBody request: AuthenticationRequest
    ): ResponseEntity<*> {
        return authService.authenticate(request)?.let{
            ResponseEntity.ok(it)
        }?: ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed")
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
        authService.refreshToken(request, response)
    }
}