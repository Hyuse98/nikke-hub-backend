package com.hyuse.nikkeManager.controller


import com.hyuse.nikkeManager.DTO.NikkeDTO
import com.hyuse.nikkeManager.model.Nikke
import com.hyuse.nikkeManager.repository.NikkeRepository
import com.hyuse.nikkeManager.service.NikkeService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/nikke")
class NikkeController(val nikkeService: NikkeService, val nikkeRepository: NikkeRepository) {

    @GetMapping("/{name}")
    fun getNikke(@PathVariable name: String): Nikke {
        val nikke = nikkeRepository.findByName(name);
        return nikke.get()
    }

    @GetMapping
    fun listNikke(): List<Nikke> {
        return nikkeRepository.findAll()
    }

    @PostMapping
    fun createNikke(@RequestBody @Valid nikkeDTO: NikkeDTO): ResponseEntity<Nikke> {
        return ResponseEntity.status(HttpStatus.CREATED).body(nikkeService.createNikke(nikkeDTO))
    }

    @PutMapping("/{name}")
    fun updateNikke(@RequestBody @Valid nikkeDTO: NikkeDTO, @PathVariable name: String): ResponseEntity<Nikke>{
        return ResponseEntity.status(HttpStatus.CREATED).body(nikkeService.updateNikke(nikkeDTO, name))
    }

    @DeleteMapping("/{name}")
    fun deleteNikke(@PathVariable name: String) {
        return nikkeService.deleteNikke(name)
    }

}