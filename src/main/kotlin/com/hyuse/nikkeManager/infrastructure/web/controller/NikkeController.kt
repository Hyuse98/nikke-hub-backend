package com.hyuse.nikkeManager.infrastructure.web.controller


import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.enums.*
import com.hyuse.nikkeManager.domain.usecases.nikke.CreateNikkeCase
import com.hyuse.nikkeManager.infrastructure.web.dto.NikkeDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestBody
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@RestController
@RequestMapping("/api/nikke")
class NikkeController(
    private val createNikkeCase: CreateNikkeCase
) {

    @Operation(
        method = "POST",
        summary = "Create New Nikke",
        description = "This endpoint created nikke on database",
        tags = ["Nikke"],
        requestBody = SwaggerRequestBody(
            description = "Created new nikke",
            required = true,
            content = [Content(
                mediaType = "application/json",
                schema = Schema(
                    example = """{
                    "name": "string",
                    "core": 0,
                    "attraction": 0,
                    "skill1Level": 0,
                    "skill2Level": 0,
                    "burstLevel": 0,
                    "rarity": "R",
                    "ownedStatus": "NOT_OWNED",
                    "burstType": "I",
                    "company": "ELYSION",
                    "code": "FIRE",
                    "weapon": "AR",
                    "nikkeClass": "ATTACKER",
                    "cube": "ASSAULT",
                    "doll": {
                        "id": 0
                    }
                }"""
                )
            )]
        )
    )
    @PostMapping
    fun createNikke(@RequestBody @Valid nikkeDTO: NikkeDTO): ResponseEntity<EntityModel<Nikke>> {

        val useCaseInput = CreateNikkeCase.Input(
            name = nikkeDTO.name,
            core = nikkeDTO.core,
            attraction = nikkeDTO.attraction,
            skill1 = nikkeDTO.skill1,
            skill2 = nikkeDTO.skill2,
            skillBurst = nikkeDTO.skillBurst,
            rarity = nikkeDTO.rarity,
            ownedStatus = nikkeDTO.ownedStatus,
            burstType = nikkeDTO.burstType,
            company = nikkeDTO.company,
            code = nikkeDTO.code,
            weapon = nikkeDTO.weapon,
            nikkeClass = nikkeDTO.nikkeClass,
            cube = nikkeDTO.cube
        )

        val nikke = createNikkeCase.execute(useCaseInput)

        val entityModel = EntityModel.of(
            nikke,
            linkTo(methodOn(this::class.java).getNikke(nikke.name)).withSelfRel(),
            linkTo(methodOn(this::class.java).listAllNikke(pageable = Pageable.unpaged())).withRel("Collection")
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel)
    }

    @Operation(
        method = "PUT",
        summary = "Update a Nikke by Name",
        description = "This endpoint update exist nikke data on database, match name passed on path variable",
        tags = ["Nikke"],
        requestBody = SwaggerRequestBody(
            description = "Update a nikke object",
            required = true,
            content = [Content(
                mediaType = "application/json",
                schema = Schema(
                    example = """{
                    "core": 0,
                    "attraction": 0,
                    "skill1Level": 0,
                    "skill2Level": 0,
                    "burstLevel": 0,
                    "cube": "ASSAULT",
                    "doll": {
                        "id": 0
                    }
                }"""
                )
            )]
        )
    )
    @PutMapping("/{name}")
    fun updateNikke(
        @RequestBody @Valid nikkeDTO: NikkeDTO,
        @PathVariable name: String
    ): ResponseEntity<Nikke> {
        return ResponseEntity.status(HttpStatus.CREATED).body(nikkeService.updateNikke(nikkeDTO, name))
    }

    @Operation(
        method = "DELETE",
        summary = "Delete a Nikke by Name",
        description = "This endpoint delete a nikke by his name on path variable",
        tags = ["Nikke"],
    )
    @DeleteMapping("/{name}")
    fun deleteNikke(@PathVariable name: String) {
        return nikkeService.deleteNikke(name)
    }

    @Operation(
        method = "GET",
        summary = "List all Nikkes Filtered",
        description = "This endpoint will return a list of all nikkes available on database with filters applied",
        tags = ["Nikke"],
    )
    @GetMapping("/filtered")
    fun listAllNikkeFiltered(
        @RequestParam(required = false) rarity: Rarity?,
        @RequestParam(required = false) ownedStatus: OwnedStatus?,
        @RequestParam(required = false) burstType: BurstType?,
        @RequestParam(required = false) company: Company?,
        @RequestParam(required = false) code: Code?,
        @RequestParam(required = false) weapon: Weapon?,
        @RequestParam(required = false) nikkeClass: NikkeClass?,
        @RequestParam(required = false) cube: Cubes?,
        @ParameterObject pageable: Pageable
    ): ResponseEntity<PagedModel<EntityModel<Nikke>>> {

        val nikkesPage = nikkeService.listAllNikkeFiltered(
            rarity, ownedStatus, burstType, company, code, weapon, nikkeClass, cube, pageable
        )

        val nikkes = nikkesPage.content.map { nikke ->
            EntityModel.of(
                nikke,
                linkTo(methodOn(this::class.java).getNikke(nikke.name)).withSelfRel()
            )
        }

        val pagedModel = PagedModel.of(
            nikkes,
            PagedModel.PageMetadata(
                nikkesPage.size.toLong(),
                nikkesPage.number.toLong(),
                nikkesPage.totalElements,
                nikkesPage.totalPages.toLong()
            )
        )
        return ResponseEntity.ok(pagedModel)
    }

    @Operation(
        method = "GET",
        summary = "List all Nikkes no Filter",
        description = "This endpoint will return a list of all nikkes available on database without filters applied",
        tags = ["Nikke"],
    )
    @GetMapping
    fun listAllNikke(@ParameterObject pageable: Pageable): ResponseEntity<PagedModel<EntityModel<Nikke>>> {

        val nikkesPage = nikkeService.listAllNikke(pageable)

        val nikkes = nikkesPage.content.map { nikke ->
            EntityModel.of(
                nikke,
                linkTo(methodOn(this::class.java).getNikke(nikke.name)).withSelfRel()
            )
        }

        val pagedModel = PagedModel.of(
            nikkes,
            PagedModel.PageMetadata(
                nikkesPage.size.toLong(),
                nikkesPage.number.toLong(),
                nikkesPage.totalElements,
                nikkesPage.totalPages.toLong()
            ),
            linkTo(methodOn(this::class.java).listAllNikke(pageable)).withSelfRel()
        )
        return ResponseEntity.ok(pagedModel)
    }

    @Operation(
        method = "GET",
        summary = "Search a nikke",
        description = "This endpoint will return a nikke that match with name passed on path variable",
        tags = ["Nikke"],
    )
    @GetMapping("/{name}")
    fun getNikke(@Parameter(description = "Name of nikke to be search") @PathVariable name: String): ResponseEntity<EntityModel<Nikke>> {

        val nikke = nikkeService.searchNikke(name)
        val entityModel = EntityModel.of(
            nikke!!,
            linkTo(methodOn(this::class.java).listAllNikke(pageable = Pageable.unpaged())).withRel("Collection")
        )
        return ResponseEntity.ok(entityModel)
    }
}