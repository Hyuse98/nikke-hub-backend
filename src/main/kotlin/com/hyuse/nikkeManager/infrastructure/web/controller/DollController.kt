package com.hyuse.nikkeManager.infrastructure.web.controller

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.usecases.doll.*
import com.hyuse.nikkeManager.infrastructure.web.dto.DollDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@RestController
@RequestMapping("/api/doll")
class DollController(
    private val createDollCase: CreateDollCase,
    private val updateDollCase: UpdateDollCase,
    private val deleteDollCase: DeleteDollCase,
    private val getDollByIdCase: GetDollByIdCase,
//    private val getDollsByRarityCase: GetDollsByRarityCase,
    private val getAllDollsCase: GetAllDollsCase
) {


    @PostMapping
    fun createDoll(@RequestBody @Valid dollDTO: DollDTO): ResponseEntity<EntityModel<Doll>> {

        val doll = createDollCase.execute(dollDTO.rarity, dollDTO.level)

        val entityModel = EntityModel.of(
            doll,
//            linkTo(methodOn(this::class.java).getDollById(doll.id!!)).withSelfRel(),
//            linkTo(methodOn(this::class.java).deleteDollById(doll.id)).withRel("Delete"),
            linkTo(methodOn(this::class.java).getListDolls()).withRel("Collection")
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel)
    }


    @PutMapping
    fun updateDoll(@RequestBody @Valid dollDTO: DollDTO): ResponseEntity<EntityModel<Doll>> {

        val doll = updateDollCase.execute(dollDTO.id!!, dollDTO.rarity, dollDTO.level)

        val entityModel = EntityModel.of(
            doll,
//            linkTo(methodOn(this::class.java).getDollById(doll.id!!)).withSelfRel(),
//            linkTo(methodOn(this::class.java).deleteDollById(doll.id)).withRel("Delete"),
            linkTo(methodOn(this::class.java).getListDolls()).withRel("Collection")
        )

        return ResponseEntity.status(HttpStatus.OK).body(entityModel)
    }



    @GetMapping
    fun getListDolls(): ResponseEntity<Collection<Doll>> {

        val dolls = getAllDollsCase.execute()

//        val entityModel = dolls?.let {
//            EntityModel.of(
//                it,
////                linkTo(methodOn(this::class.java).getDollById(dolls.id!!)).withSelfRel(),
//                linkTo(methodOn(this::class.java).getListDolls()).withRel("Collection")
//            )
//        }

        return ResponseEntity.ok(dolls)
    }


//    @Operation(
//        method = "GET",
//        summary = "Get a doll",
//        description = "This endpoint will search a doll on database that match rarity and level passed was parameter",
//        tags = ["Doll"]
//    )
//    @GetMapping("/search")
//    fun getDollByRarityAndLevel(
//        @RequestParam rarity: Rarity,
//        @RequestParam level: Int
//    ): ResponseEntity<EntityModel<Doll>> {
//
//        TODO("Not Resources Available yet")
//        val doll = getDollsByRarityCase.execute(rarity)
//
//        val entityModel = doll?.let {
//            EntityModel.of(
//                it,
//                linkTo(methodOn(this::class.java).getDollById(doll.id!!)).withSelfRel(),
//                linkTo(methodOn(this::class.java).getListDolls()).withRel("Collection")
//            )
//        }
//
//        return ResponseEntity.ok(entityModel)
//    }


    @GetMapping("/{id}")
    fun getDollById(@PathVariable id: Int): ResponseEntity<EntityModel<Doll>> {

        val doll = getDollByIdCase.execute(id)

        val entityModel = EntityModel.of(
            doll,
//            linkTo(methodOn(this::class.java).getDollById(doll.id!!)).withSelfRel(),
            linkTo(methodOn(this::class.java).getListDolls()).withRel("Collection")
        )

        return ResponseEntity.ok(entityModel)
    }


    @DeleteMapping("/{id}")
    fun deleteDollById(@PathVariable id: Int): ResponseEntity<EntityModel<Link>> {

        deleteDollCase.execute(id)

        val entityModel = EntityModel.of(
            linkTo(methodOn(this::class.java).getListDolls()).withRel("Collection")
        )

        return ResponseEntity.ok(entityModel)
    }
}