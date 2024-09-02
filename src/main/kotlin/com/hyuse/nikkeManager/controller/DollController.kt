package com.hyuse.nikkeManager.controller

import com.hyuse.nikkeManager.DTO.DollDTO
import com.hyuse.nikkeManager.model.Doll
import com.hyuse.nikkeManager.repository.DollRepository
import com.hyuse.nikkeManager.service.DollService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/doll")
class DollController(val dollRepository: DollRepository, val dollService: DollService) {

    @PostMapping
    fun createDoll(@RequestBody @Valid dollDTO: DollDTO): ResponseEntity<Doll> {
        return ResponseEntity.status(HttpStatus.CREATED).body(dollService.addDoll(dollDTO))
    }
}