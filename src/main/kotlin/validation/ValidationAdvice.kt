package validation

import model.ValidationErrors
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ValidationAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValid(exception: MethodArgumentNotValidException): ValidationErrors {
        val errors = ValidationErrors()

        for (fieldError in exception.bindingResult.fieldErrors) {
            errors.addError(fieldError.defaultMessage!!)
        }

        return errors
    }
}