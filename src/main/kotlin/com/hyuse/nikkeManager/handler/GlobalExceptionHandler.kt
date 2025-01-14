package com.hyuse.nikkeManager.handler

import com.hyuse.nikkeManager.exception.NikkeAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler{

    @ExceptionHandler(NikkeAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handlerNikkeAlreadyExists(ex: NikkeAlreadyExistsException): ExceptionResponse {
        return ExceptionResponse(
            status = HttpStatus.CONFLICT,
            message = ex.message ?: "Nikke Already Exist"
        )
    }
}