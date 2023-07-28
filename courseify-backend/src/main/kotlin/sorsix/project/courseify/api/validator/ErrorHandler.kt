package sorsix.project.courseify.api.validator

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import sorsix.project.courseify.domain.exception.ExistingUsernameException
import sorsix.project.courseify.domain.exception.PasswordsDoNotMatchException

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
}