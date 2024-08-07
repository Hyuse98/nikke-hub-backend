package com.hyuse.nikkeManager.controller

import com.hyuse.nikkeManager.DTO.NikkeDTO
import com.hyuse.nikkeManager.model.Nikke
import com.hyuse.nikkeManager.repository.NikkeRepository
import com.hyuse.nikkeManager.service.NikkeService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/nikke")
class NikkeController (val nikkeService: NikkeService, val nikkeRepository: NikkeRepository){

    //TODO ??
    @GetMapping("/{id}")
    fun getNikke(){

    }
    //TODO ??
    @GetMapping
    fun listNikke(): List<Nikke>{
        return nikkeRepository.findAll()
    }
    //TODO rules
    @PostMapping
    fun createNikke(@RequestBody @Valid nikkeDTO: NikkeDTO): ResponseEntity<Nikke>{
        return ResponseEntity.status(HttpStatus.CREATED).body(nikkeService.addNikke(nikkeDTO))
    }
    //TODO
    fun updateNikke(){

    }
    //TODO
    fun deleteNikke(){

    }
}