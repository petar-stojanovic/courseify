package sorsix.project.courseify.service.definitions

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import sorsix.project.courseify.api.request.AuthenticationRequest
import sorsix.project.courseify.api.request.RegisterRequest
import sorsix.project.courseify.domain.response.AuthenticationResponse

interface AuthService {

    fun register(request: RegisterRequest): AuthenticationResponse


    fun authenticate(request: AuthenticationRequest): AuthenticationResponse

    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse)

}