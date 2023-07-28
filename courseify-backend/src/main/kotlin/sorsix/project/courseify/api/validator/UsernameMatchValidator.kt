package sorsix.project.courseify.api.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import sorsix.project.courseify.api.request.RegisterRequest

class UsernameMatchValidator : ConstraintValidator<UsernameMatchConstraint, RegisterRequest> {

    override fun isValid(value: RegisterRequest, context: ConstraintValidatorContext?): Boolean =
        value.password == value.confirmPassword

}