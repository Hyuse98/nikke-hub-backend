package com.hyuse.nikkeManager.shared.exceptions

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ExceptionResponse(
    val status: HttpStatus,
    val message: String,
    val errors: List<String> = listOf(),
    val timestamp: LocalDateTime = LocalDateTime.now()
)