package sorsix.project.courseify.api.validator

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import sorsix.project.courseify.domain.exception.*

@ResponseBody
@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler(ExistingUsernameException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun onUsernameInvalidException(e: ExistingUsernameException): Map<String, String> {
        return mapOf("error" to (e.message ?: ""))
    }


    @ExceptionHandler(PasswordsDoNotMatchException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun onPasswordsDoNotMatchException(
        e: PasswordsDoNotMatchException,
        response: HttpServletResponse
    ): Map<String, String> {
        response.setHeader("X-Auth-Error-Reason", "passwordsDoNotMatch")
        return mapOf("error" to (e.message ?: ""))
    }

    @ExceptionHandler(InvalidCredentialsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun onInvalidCredentialsException(
        e: InvalidCredentialsException,
        response: HttpServletResponse
    ): Map<String, String> {
        response.setHeader("X-Auth-Error-Reason", "invalidCredentials")
        return mapOf("error" to (e.message ?: ""))
    }

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun onUserNotFoundException(e: UserNotFoundException): Map<String, String> {
        return mapOf("error" to (e.message ?: ""))
    }

    @ExceptionHandler(WrongPasswordException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun onWrongPasswordException(
        e: WrongPasswordException,
        response: HttpServletResponse
    ): Map<String, String> {
        response.setHeader("X-Auth-Error-Reason", "wrongPassword")
        return mapOf("error" to (e.message ?: ""))
    }
}