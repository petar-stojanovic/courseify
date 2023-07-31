package sorsix.project.courseify.api.validator

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import sorsix.project.courseify.domain.exception.ExistingUsernameException
import sorsix.project.courseify.domain.exception.PasswordsDoNotMatchException
import sorsix.project.courseify.domain.exception.UserNotFoundException

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
    fun onPasswordsDoNotMatchException(e: PasswordsDoNotMatchException): Map<String, String> {
        return mapOf("error" to (e.message ?: ""))
    }

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun onUserNotFoundException(e: UserNotFoundException): Map<String, String> {
        return mapOf("error" to (e.message ?: ""))
    }
}