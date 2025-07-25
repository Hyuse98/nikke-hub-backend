package com.hyuse.nikkeManager.infrastructure.web.controller


import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.usecases.nikke.*
import com.hyuse.nikkeManager.infrastructure.web.dto.NikkeDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import org.springframework.hateoas.EntityModel
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
    private val createNikkeCase: CreateNikkeCase,
    private val correctNikkeBaseDataCase: CorrectNikkeBaseDataCase,
    private val deleteNikkeCase: DeleteNikkeCase,
    private val getNikkeByNameCase: GetNikkeByNameCase,
    private val getAllNikkesCase: GetAllNikkesCase
) {


    @PostMapping
    fun createNikke(@RequestBody @Valid nikkeDTO: NikkeDTO): ResponseEntity<EntityModel<Nikke>> {

        val nikke = createNikkeCase.execute(
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
            nikkeClass = nikkeDTO.nikkeClass
        )

        val entityModel = EntityModel.of(
            nikke,
            linkTo(methodOn(this::class.java).getNikke(nikke.name.value)).withSelfRel(),
            linkTo(methodOn(this::class.java).listAllNikke()).withRel("Collection")
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel)
    }

    @PutMapping("/{id}")
    fun CorrectNikkeBaseDataCase(
        @RequestBody @Valid nikkeDTO: NikkeDTO,
        @PathVariable id: Int
    ): ResponseEntity<Nikke> {

        return ResponseEntity.status(HttpStatus.OK).body(
            correctNikkeBaseDataCase.execute(
                id = nikkeDTO.id!!,
                name = nikkeDTO.name,
                rarity = nikkeDTO.rarity,
                burstType = nikkeDTO.burstType,
                company = nikkeDTO.company,
                code = nikkeDTO.code,
                weapon = nikkeDTO.weapon,
                nikkeClass = nikkeDTO.nikkeClass,
            )
        )
    }

    @DeleteMapping("/{id}")
    fun deleteNikke(@PathVariable id: Int) {
        return deleteNikkeCase.execute(id)
    }

    @GetMapping
    fun listAllNikke(): ResponseEntity<Collection<Nikke>> {

        val nikkes = getAllNikkesCase.execute()

        return ResponseEntity.ok(nikkes)
    }

    @GetMapping("/{name}")
    fun getNikke(@Parameter(description = "Name of nikke to be search") @PathVariable name: String): ResponseEntity<EntityModel<Nikke>> {

        val nikke = getNikkeByNameCase.execute(name)
        val entityModel = EntityModel.of(
            nikke,
            linkTo(methodOn(this::class.java).listAllNikke()).withRel("Collection")
        )
        return ResponseEntity.ok(entityModel)
    }
}