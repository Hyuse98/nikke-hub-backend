package com.hyuse.nikkeManager.controller

import com.hyuse.nikkeManager.dto.DollDTO
import com.hyuse.nikkeManager.model.Doll
import com.hyuse.nikkeManager.repository.DollRepository
import com.hyuse.nikkeManager.service.DollService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/doll")
class DollController(val dollRepository: DollRepository, val dollService: DollService) {

    @GetMapping
    fun listDolls(): List<Doll>{
        return dollService.listDolls()
    }

    @Operation(
        method = "POST",
        summary = "Cria uma Doll",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Created doll object",
            required = true,
            content = [Content(
                schema = Schema(
                    example = """{
                    "doll": {
                        "id": 0,
                        "rarity": SR,
                        "level": 0
                    }
                }"""
                )
            )]
        )
    )
    @PostMapping
    fun createDoll(@RequestBody @Valid dollDTO: DollDTO): ResponseEntity<Doll> {
        return ResponseEntity.status(HttpStatus.CREATED).body(dollService.createDoll(dollDTO))
    }
}