package com.hyuse.nikkeManager.handler

import com.hyuse.nikkeManager.exception.*
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NikkeAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handlerNikkeAlreadyExists(ex: NikkeAlreadyExistsException): ExceptionResponse {
        return ExceptionResponse(
            status = HttpStatus.CONFLICT,
            message = ex.message ?: "Nikke Already Exist"
        )
    }

    @ExceptionHandler(NikkeNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handlerNikkeNotFound(ex: NikkeNotFoundException): ExceptionResponse {
        return ExceptionResponse(
            status = HttpStatus.NOT_FOUND,
            message = ex.message ?: "Nikke Not Found"
        )
    }

    @ExceptionHandler(NikkeIdNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handlerNikkeIdNotFound(ex: NikkeIdNotFoundException): ExceptionResponse {
        return ExceptionResponse(
            status = HttpStatus.NOT_FOUND,
            message = ex.message ?: "Nikke Not Found"
        )
    }

    @ExceptionHandler(DollAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handlerDollAlreadyExistsException(ex: DollAlreadyExistsException): ExceptionResponse{
        return ExceptionResponse(
            status = HttpStatus.CONFLICT,
            message = ex.message ?: "Doll already exist"
        )
    }

    @ExceptionHandler(DollNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handlerDollNotFoundException(ex: DollNotFoundException): ExceptionResponse{
        return ExceptionResponse(
            status = HttpStatus.NOT_FOUND,
            message = ex.message ?: "Doll not found"
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ExceptionResponse {

        val errorsList = mutableListOf<String>()

        for (errors in ex.bindingResult.allErrors) {
            val fields = errors as FieldError
            val fieldNames = fields.field
            val errorMessage = errors.defaultMessage ?: "Invalid Values"
            val message = "$fieldNames: $errorMessage"
            errorsList.add(message)
        }

        return ExceptionResponse(
            status = HttpStatus.BAD_REQUEST,
            message = "Fields Validation Failed",
            errors = errorsList
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ExceptionResponse {
        return ExceptionResponse(
            status = HttpStatus.BAD_REQUEST,
            message = "Malformed JSON Request",
            errors = listOf(ex.localizedMessage)
        )
    }
}