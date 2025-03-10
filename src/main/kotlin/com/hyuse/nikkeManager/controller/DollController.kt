package com.hyuse.nikkeManager.controller

import com.hyuse.nikkeManager.dto.DollDTO
import com.hyuse.nikkeManager.enums.Rarity
import com.hyuse.nikkeManager.model.Doll
import com.hyuse.nikkeManager.repository.DollRepository
import com.hyuse.nikkeManager.service.DollService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@RestController
@RequestMapping("/api/doll")
class DollController(
    val dollRepository: DollRepository,
    val dollService: DollService
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
        val doll = dollService.createDoll(dollDTO) ?: throw IllegalStateException()
        val entityModel = EntityModel.of(
            doll,
            linkTo(methodOn(this::class.java).getDollById(doll.id!!)).withSelfRel(),
            linkTo(methodOn(this::class.java).deleteDollById(doll.id)).withRel("Delete"),
            linkTo(methodOn(this::class.java).getListDolls(pageable = Pageable.unpaged())).withRel("Collection")

        )
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel)
    }

    @Operation(
        method = "GET",
        summary = "List all dolls",
        description = "This endpoint will list all dolls on database",
        tags = ["Doll"]
    )
    @GetMapping
    fun getListDolls(@ParameterObject pageable: Pageable): ResponseEntity<PagedModel<EntityModel<Doll>>> {
        val dollsPage = dollService.getListDolls(pageable)
        val dolls = dollsPage.content.map { doll ->
            EntityModel.of(
                doll,
                linkTo(methodOn(this::class.java).getDollById(doll.id!!)).withSelfRel()
            )
        }
        val pagedModel = PagedModel.of(
            dolls,
            PagedModel.PageMetadata(
                dollsPage.size.toLong(),
                dollsPage.number.toLong(),
                dollsPage.totalElements,
                dollsPage.totalPages.toLong()
            ),
            linkTo(methodOn(this::class.java).getListDolls(pageable)).withSelfRel()
        )
        return ResponseEntity.ok(pagedModel)
    }


    @Operation(
        method = "GET",
        summary = "Get a doll",
        description = "This endpoint will search a doll on database that match ratity and level passed was paramater",
        tags = ["Doll"]
    )
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
                linkTo(methodOn(this::class.java).getListDolls(pageable = Pageable.unpaged())).withRel("Collection")
            )
        }
        return ResponseEntity.ok(entityModel)
    }

    @Operation(
        method = "GET",
        summary = "Get a doll",
        description = "This endpoint will search a doll on database that match id passed was paramater",
        tags = ["Doll"]
    )
    @GetMapping("/{id}")
    fun getDollById(@PathVariable id: Int): ResponseEntity<EntityModel<Doll>> {
        val doll = dollRepository.findById(id).orElseThrow()
        val entityModel = EntityModel.of(
            doll,
            linkTo(methodOn(this::class.java).getDollById(doll.id!!)).withSelfRel(),
            linkTo(methodOn(this::class.java).getListDolls(pageable = Pageable.unpaged())).withRel("Collection")
        )
        return ResponseEntity.ok(entityModel)
    }

    @Operation(
        method = "DELETE",
        summary = "Delete a doll",
        description = "This endpoint will delete a doll on database that match id passed was paramater",
        tags = ["Doll"]
    )
    @DeleteMapping("/{id}")
    fun deleteDollById(@PathVariable id: Int): ResponseEntity<EntityModel<Link>> {
        dollService.deleteDollById(id)
        val entityModel = EntityModel.of(
            linkTo(methodOn(this::class.java).getListDolls(pageable = Pageable.unpaged())).withRel("Collection")
        )
        return ResponseEntity.ok(entityModel)
    }
}