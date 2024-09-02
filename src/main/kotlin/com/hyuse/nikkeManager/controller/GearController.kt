package com.hyuse.nikkeManager.controller

import com.hyuse.nikkeManager.DTO.GearDTO
import com.hyuse.nikkeManager.model.Gear
import com.hyuse.nikkeManager.repository.GearRepository
import com.hyuse.nikkeManager.service.GearService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/gear")
class GearController(val gearService: GearService, val gearRepository: GearRepository) {

    @GetMapping
    fun listGear(): List<Gear> {
        return gearRepository.findAll()
    }

    @PostMapping
    fun createGear(@RequestBody gearDTO: GearDTO): ResponseEntity<Gear> {
        return ResponseEntity.status(HttpStatus.CREATED).body(gearService.createGear(gearDTO))
    }
}