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
    fun createNikke(@RequestBody @Valid nikkeDTO: NikkeDTO): ResponseEntity<Nikke> {
        return ResponseEntity.status(HttpStatus.CREATED).body(nikkeService.createNikke(nikkeDTO))
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
    fun updateNikke(@RequestBody @Valid nikkeDTO: NikkeDTO, @PathVariable name: String): ResponseEntity<Nikke> {
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
    @GetMapping
    fun listAllNikke(
        @RequestParam(required = false) rarity: Rarity?,
        @RequestParam(required = false) ownedStatus: OwnedStatus?,
        @RequestParam(required = false) burstType: BurstType?,
        @RequestParam(required = false) company: Company?,
        @RequestParam(required = false) code: Code?,
        @RequestParam(required = false) weapon: Weapon?,
        @RequestParam(required = false) nikkeClass: NikkeClass?,
        @RequestParam(required = false) cube: Cubes?
    ): List<Nikke> {
        return nikkeService.listAllNikke(rarity, ownedStatus, burstType, company, code, weapon, nikkeClass, cube)
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
    fun getNikke(@Parameter(description = "Name of nikke to be search") @PathVariable name: String): Nikke {
        val nikke = nikkeRepository.findByName(name);
        return nikke.get()
    }
}