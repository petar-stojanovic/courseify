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
    fun onExistingUsernameException(
        e: ExistingUsernameException,
        response: HttpServletResponse
    ): Map<String, String> {
        response.setHeader("X-Auth-Error-Reason", "usernameExists")
        return mapOf("error" to (e.message ?: ""))
    }

    @ExceptionHandler(ExistingEmailException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun onExistingEmailException(
        e: ExistingEmailException,
        response: HttpServletResponse
    ): Map<String, String> {
        response.setHeader("X-Auth-Error-Reason", "emailExists")
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


}