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
    private val getDollsByRarityCase: GetDollsByRarityCase,
    private val getAllDollsCase: GetAllDollsCase
) {

    @Operation(
        method = "POST",
        summary = "Create a new doll",
        description = "This endpoint create a doll on database",
        tags = ["Doll"],
        requestBody = SwaggerRequestBody(
            description = "Create doll object",
            required = true,
            content = [Content(
                mediaType = "application/json",
                schema = Schema(
                    example = """{
                        "rarity" SSR,
                        "level" 15
                    }"""
                )
            )]
        )
    )
    @PostMapping
    fun createDoll(@RequestBody @Valid dollDTO: DollDTO): ResponseEntity<EntityModel<Doll>> {

        val doll = createDollCase.execute(dollDTO.rarity, dollDTO.level)

        val entityModel = EntityModel.of(
            doll,
            linkTo(methodOn(this::class.java).getDollById(doll.id!!)).withSelfRel(),
            linkTo(methodOn(this::class.java).deleteDollById(doll.id)).withRel("Delete"),
            linkTo(methodOn(this::class.java).getListDolls()).withRel("Collection")
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel)
    }

    @Operation(
        method = "PUT",
        summary = "Update a doll",
        description = "This endpoint update a doll on database",
        tags = ["Doll"],
        requestBody = SwaggerRequestBody(
            description = "Update doll object",
            required = true,
            content = [Content(
                mediaType = "application/json",
                schema = Schema(
                    example = """{
                        "id" 1,
                        "rarity" SSR,
                        "level" 15
                    }"""
                )
            )]
        )
    )
    @PutMapping
    fun updateDoll(@RequestBody @Valid dollDTO: DollDTO): ResponseEntity<EntityModel<Doll>> {

        val doll = updateDollCase.execute(dollDTO.id!!, dollDTO.rarity, dollDTO.level)

        val entityModel = EntityModel.of(
            doll,
            linkTo(methodOn(this::class.java).getDollById(doll.id!!)).withSelfRel(),
            linkTo(methodOn(this::class.java).deleteDollById(doll.id)).withRel("Delete"),
            linkTo(methodOn(this::class.java).getListDolls()).withRel("Collection")
        )

        return ResponseEntity.status(HttpStatus.OK).body(entityModel)
    }


    @Operation(
        method = "GET",
        summary = "List all dolls",
        description = "This endpoint will list all dolls on database",
        tags = ["Doll"]
    )
    @GetMapping
    fun getListDolls(): ResponseEntity<Collection<Doll>> {

        val dolls = getAllDollsCase.execute()

        val entityModel = dolls?.let {
            EntityModel.of(
                it,
//                linkTo(methodOn(this::class.java).getDollById(dolls.id!!)).withSelfRel(),
                linkTo(methodOn(this::class.java).getListDolls()).withRel("Collection")
            )
        }

        return ResponseEntity.ok(dolls)
    }


    @Operation(
        method = "GET",
        summary = "Get a doll",
        description = "This endpoint will search a doll on database that match rarity and level passed was parameter",
        tags = ["Doll"]
    )
    @GetMapping("/search")
    fun getDollByRarityAndLevel(
        @RequestParam rarity: Rarity,
        @RequestParam level: Int
    ): ResponseEntity<EntityModel<Doll>> {

        TODO("Not Resources Available yet")
        val doll = getDollsByRarityCase.execute(rarity)

        val entityModel = doll?.let {
            EntityModel.of(
                it,
                linkTo(methodOn(this::class.java).getDollById(doll.id!!)).withSelfRel(),
                linkTo(methodOn(this::class.java).getListDolls()).withRel("Collection")
            )
        }

        return ResponseEntity.ok(entityModel)
    }

    @Operation(
        method = "GET",
        summary = "Get a doll",
        description = "This endpoint will search a doll on database that match id passed was parameter",
        tags = ["Doll"]
    )
    @GetMapping("/{id}")
    fun getDollById(@PathVariable id: Int): ResponseEntity<EntityModel<Doll>> {

        val doll = getDollByIdCase.execute(id)

        val entityModel = EntityModel.of(
            doll,
            linkTo(methodOn(this::class.java).getDollById(doll.id!!)).withSelfRel(),
            linkTo(methodOn(this::class.java).getListDolls()).withRel("Collection")
        )

        return ResponseEntity.ok(entityModel)
    }

    @Operation(
        method = "DELETE",
        summary = "Delete a doll",
        description = "This endpoint will delete a doll on database that match id passed was parameter",
        tags = ["Doll"]
    )
    @DeleteMapping("/{id}")
    fun deleteDollById(@PathVariable id: Int): ResponseEntity<EntityModel<Link>> {

        deleteDollCase.execute(id)

        val entityModel = EntityModel.of(
            linkTo(methodOn(this::class.java).getListDolls()).withRel("Collection")
        )

        return ResponseEntity.ok(entityModel)
    }
}