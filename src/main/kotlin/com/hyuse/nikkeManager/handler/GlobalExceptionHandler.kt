package com.hyuse.nikkeManager.handler

import com.hyuse.nikkeManager.exception.NikkeAlreadyExistsException
import com.hyuse.nikkeManager.exception.NikkeIdNotFoundException
import com.hyuse.nikkeManager.exception.NikkeNotFoundException
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

    @ExceptionHandler(NikkeNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handlerNikkeNotFound(ex: NikkeNotFoundException): ExceptionResponse{
        return ExceptionResponse(
            status = HttpStatus.NOT_FOUND,
            message = ex.message ?: "Nikke Not Found"
        )
    }

    @ExceptionHandler(NikkeIdNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handlerNikkeIdNotFound(ex: NikkeIdNotFoundException): ExceptionResponse{
        return ExceptionResponse(
            status = HttpStatus.NOT_FOUND,
            message = ex.message ?: "Nikke Not Found"
        )
    }
}