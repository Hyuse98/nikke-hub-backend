package com.hyuse.nikkeManager.controller

import com.hyuse.nikkeManager.dto.DollDTO
import com.hyuse.nikkeManager.enums.Rarity
import com.hyuse.nikkeManager.model.Doll
import com.hyuse.nikkeManager.repository.DollRepository
import com.hyuse.nikkeManager.service.DollService
import jakarta.validation.Valid
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/doll")
class DollController(
    val dollRepository: DollRepository,
    val dollService: DollService
) {
    @PostMapping
    fun createDoll(@RequestBody @Valid dollDTO: DollDTO): ResponseEntity<EntityModel<Doll>> {
        val doll = dollService.createDoll(dollDTO) ?: throw IllegalStateException()
        val entityModel = EntityModel.of(
            doll,
            linkTo(methodOn(this::class.java).getDollById(doll.id!!)).withSelfRel(),
            linkTo(methodOn(this::class.java).deleteDollById(doll.id)).withRel("Delete"),
            linkTo(methodOn(this::class.java).getListDolls()).withRel("Collection")

        )
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel)
    }

    @GetMapping
    fun getListDolls(): ResponseEntity<CollectionModel<EntityModel<Doll>>> {
        val dolls = dollService.getListDolls().map { doll ->
            EntityModel.of(
                doll,
                linkTo(methodOn(this::class.java).getDollById(doll.id!!)).withSelfRel()
            )
        }
        val collectionModel = CollectionModel.of(
            dolls,
            linkTo(methodOn(this::class.java).getListDolls()).withSelfRel()
        )
        return ResponseEntity.ok(collectionModel)
    }


    @GetMapping("/search")
    fun getDollByRarityAndLevel(
        @RequestParam rarity: Rarity,
        @RequestParam level: Int
    ): ResponseEntity<EntityModel<Doll>> {
        val doll = dollService.getDollByRarityAndLevel(rarity, level)
        val entityModel = doll?.let {
            EntityModel.of(
                it,
                linkTo(methodOn(this::class.java).getDollById(doll.id!!)).withSelfRel(),
                linkTo(methodOn(this::class.java).getListDolls()).withRel("Collection")
            )
        }
        return ResponseEntity.ok(entityModel)
    }

    @GetMapping("/{id}")
    fun getDollById(@PathVariable id: Int): ResponseEntity<EntityModel<Doll>> {
        val doll = dollRepository.findById(id).orElseThrow()
        val entityModel = EntityModel.of(
            doll,
            linkTo(methodOn(this::class.java).getDollById(doll.id!!)).withSelfRel(),
            linkTo(methodOn(this::class.java).getListDolls()).withRel("Collection")
        )
        return ResponseEntity.ok(entityModel)
    }

    @DeleteMapping("/{id}")
    fun deleteDollById(@PathVariable id: Int): ResponseEntity<EntityModel<Link>> {
        dollService.deleteDollById(id)
        val entityModel = EntityModel.of(
            linkTo(methodOn(this::class.java).getListDolls()).withRel("Collection")
        )
        return ResponseEntity.ok(entityModel)
    }
}