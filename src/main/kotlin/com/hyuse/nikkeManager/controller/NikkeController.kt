package com.hyuse.nikkeManager.controller


import com.hyuse.nikkeManager.dto.NikkeDTO
import com.hyuse.nikkeManager.enums.*
import com.hyuse.nikkeManager.model.Nikke
import com.hyuse.nikkeManager.repository.NikkeRepository
import com.hyuse.nikkeManager.service.NikkeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestBody
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@RestController
@RequestMapping("/nikke")
class NikkeController(val nikkeService: NikkeService, val nikkeRepository: NikkeRepository) {

    @Operation(
        method = "POST", summary = "Cria uma nikke", requestBody = SwaggerRequestBody(
            description = "Created user object", required = false, content = [Content(
                schema = Schema(
                    example = """{
                    "name": "string",
                    "core": 0,
                    "attraction": 0,
                    "skill1Level": 0,
                    "skill2Level": 0,
                    "burstLevel": 0,
                    "rarity": "R",
                    "ownedStatus": "OWNED",
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

        val nikke = nikkeService.createNikke(nikkeDTO)
        val entityModel = EntityModel.of(
            nikke,
            linkTo(methodOn(this::class.java).getNikke(nikke.name)).withSelfRel(),
            linkTo(methodOn(this::class.java).listAllNikke()).withRel("Collection")
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel)
    }

    @Operation(
        method = "PUT", summary = "Atualiza uma nikke", description = "", requestBody = SwaggerRequestBody(
            description = "Created user object", required = true, content = [Content(
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

    @Operation(method = "DELETE", summary = "Apaga uma nikke")
    @DeleteMapping("/{name}")
    fun deleteNikke(@PathVariable name: String) {
        return nikkeService.deleteNikke(name)
    }

    @Operation(method = "GET", summary = "List all Nikkes", description = "Return a list of all nikkes")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "OK",
            content = [Content(array = ArraySchema(schema = Schema(implementation = NikkeDTO::class)))]
        ), ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = [Content(schema = Schema(`$schema` = ""))]
        ), ApiResponse(
            responseCode = "5XX",
            description = "Internal Server Error",
            content = [Content(schema = Schema(`$schema` = ""))]
        )]
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
        @RequestParam(required = false) cube: Cubes?
    ): ResponseEntity<CollectionModel<EntityModel<Nikke>>> {
        val nikkes = nikkeService.listAllNikkeFiltered(rarity, ownedStatus, burstType, company, code, weapon, nikkeClass, cube)
            .map { nikke ->
                EntityModel.of(
                    nikke,
                    linkTo(methodOn(this::class.java).getNikke(nikke.name)).withSelfRel()
                )
            }

        val collectionModel = CollectionModel.of(
            nikkes,
            linkTo(methodOn(this::class.java).listAllNikkeFiltered(
                rarity = rarity,
                ownedStatus = ownedStatus,
                burstType = burstType,
                company = company,
                code = code,
                weapon = weapon,
                nikkeClass = nikkeClass,
                cube = cube
            )).withSelfRel()
        )

        return ResponseEntity.ok(collectionModel)
    }

    @GetMapping
    fun listAllNikke(): ResponseEntity<CollectionModel<EntityModel<Nikke>>> {
        val nikkes = nikkeService.listAllNikke()
            .map { nikke ->
                EntityModel.of(
                    nikke,
                    linkTo(methodOn(this::class.java).getNikke(nikke.name)).withSelfRel()
                )
            }

        val collectionModel = CollectionModel.of(
            nikkes,
            linkTo(methodOn(this::class.java).listAllNikke()).withSelfRel()
        )

        return ResponseEntity.ok(collectionModel)
    }

    @Operation(method = "GET", summary = "Search a nikke")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "OK",
            content = [Content(schema = Schema(implementation = NikkeDTO::class))]
        ), ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = [Content(schema = Schema(`$schema` = ""))]
        ), ApiResponse(
            responseCode = "5XX",
            description = "Internal Server Error",
            content = [Content(schema = Schema(`$schema` = ""))]
        )]
    )
    @GetMapping("/{name}")
    fun getNikke(@Parameter(description = "Name of nikke to be search") @PathVariable name: String): ResponseEntity<EntityModel<Nikke>> {

        val nikke = nikkeService.searchNikke(name)
        val entityModel = EntityModel.of(
            nikke!!,
            linkTo(methodOn(this::class.java).listAllNikke()).withRel("Collection")
        )
        return ResponseEntity.ok(entityModel)
    }
}