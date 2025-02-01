package com.hyuse.nikkeManager.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.hyuse.nikkeManager.dto.NikkeDTO
import com.hyuse.nikkeManager.enums.*
import com.hyuse.nikkeManager.exception.NikkeNotFoundException
import com.hyuse.nikkeManager.model.Nikke
import com.hyuse.nikkeManager.repository.NikkeRepository
import com.hyuse.nikkeManager.service.NikkeService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.justRun
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(NikkeController::class)
class NikkeControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var nikkeService: NikkeService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    lateinit var nikkeRepository: NikkeRepository

    @Test
    @DisplayName("201")
    fun createNikkeCase1() {

        val request = NikkeDTO(
            id = null,
            name = "Test1",
            core = 1,
            attraction = 1,
            skill1Level = 1,
            skill2Level = 1,
            burstLevel = 1,
            rarity = Rarity.SR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.PILGRIM,
            code = Code.ELECTRIC,
            weapon = Weapon.MG,
            nikkeClass = NikkeClass.SUPPORTER,
            cube = null,
            doll = null
        )

        val response = request.copy(id = 1)

        every { nikkeService.createNikke(request) } returns response.toModel()

        mockMvc.perform(
            post("/nikke")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Test1"))
            .andExpect(jsonPath("$.core").value(1))
            .andExpect(jsonPath("$.attraction").value(1))
            .andExpect(jsonPath("$.skill1Level").value(1))
            .andExpect(jsonPath("$.skill2Level").value(1))
            .andExpect(jsonPath("$.burstLevel").value(1))
            .andExpect(jsonPath("$.rarity").value(Rarity.SR.name))
            .andExpect(jsonPath("$.ownedStatus").value(OwnedStatus.NOT_OWNED.name))
            .andExpect(jsonPath("$.burstType").value(BurstType.III.name))
            .andExpect(jsonPath("$.company").value(Company.PILGRIM.name))
            .andExpect(jsonPath("$.code").value(Code.ELECTRIC.name))
            .andExpect(jsonPath("$.weapon").value(Weapon.MG.name))
            .andExpect(jsonPath("$.nikkeClass").value(NikkeClass.SUPPORTER.name))
            .andExpect(jsonPath("$.cube").doesNotExist())
            .andExpect(jsonPath("$.doll").doesNotExist())
    }

    @Test
    @DisplayName("400")
    fun createNikkeCase2() {

        val request = NikkeDTO(
            id = null,
            name = "name",
            core = 50,
            attraction = 1,
            skill1Level = 1,
            skill2Level = 1,
            burstLevel = 1,
            rarity = Rarity.SR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.PILGRIM,
            code = Code.ELECTRIC,
            weapon = Weapon.MG,
            nikkeClass = NikkeClass.SUPPORTER,
            cube = null,
            doll = null
        )

        every { nikkeService.createNikke(request) }

        mockMvc.perform(
            post("/nikke")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(request))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("400")
    fun createNikkeCase3() {
        val invalidRequest = """
        {
            "id": null,
            "name": "Test1",
            "core": "invalid",
            "attraction": 1,
            "skill1Level": 1,
            "skill2Level": 1,
            "burstLevel": 1,
            "rarity": "SR",
            "ownedStatus": "NOT_OWNED",
            "burstType": "III",
            "company": "PILGRIM",
            "code": "ELECTRIC",
            "weapon": "MG",
            "nikkeClass": "SUPPORTER",
            "cube": null,
            "doll": null
        }
    """.trimIndent()

        mockMvc.perform(
            post("/nikke")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors[*]").value("JSON parse error: Cannot deserialize value of type `int` from String \"invalid\": not a valid `int` value"))
    }

    @Test
    @DisplayName("201")
    fun updateNikkeCase1() {

        val nikkeDTO = NikkeDTO(
            id = 1,
            name = "Rapi",
            core = 0,
            attraction = 1,
            skill1Level = 1,
            skill2Level = 1,
            burstLevel = 1,
            rarity = Rarity.SSR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.PILGRIM,
            code = Code.ELECTRIC,
            weapon = Weapon.MG,
            nikkeClass = NikkeClass.ATTACKER,
            cube = null,
            doll = null
        )

        val name = "Rapi"

        val updatedNikke = nikkeDTO.toModel()

        every { nikkeService.updateNikke(nikkeDTO, name) } returns updatedNikke

        mockMvc.perform(
            put("/nikke/{name}", name)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nikkeDTO))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.name").value(nikkeDTO.name))
            .andExpect(jsonPath("$.core").value(nikkeDTO.core))
    }

    @Test
    @DisplayName("400")
    fun updateNikkeCase2() {

        val request = NikkeDTO(
            id = null,
            name = "name",
            core = 50,
            attraction = 1,
            skill1Level = 1,
            skill2Level = 1,
            burstLevel = 1,
            rarity = Rarity.SR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.PILGRIM,
            code = Code.ELECTRIC,
            weapon = Weapon.MG,
            nikkeClass = NikkeClass.SUPPORTER,
            cube = null,
            doll = null
        )

        val name = "Rapi"

        every { nikkeService.updateNikke(request, name) }

        mockMvc.perform(
            put("/nikke/{name}", name)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(request))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("400")
    fun updateNikkeCase3() {
        val invalidRequest = """
        {
            "id": null,
            "name": "Rapi",
            "core": "invalid",
            "attraction": 1,
            "skill1Level": 1,
            "skill2Level": 1,
            "burstLevel": 1,
            "rarity": "SR",
            "ownedStatus": "NOT_OWNED",
            "burstType": "III",
            "company": "PILGRIM",
            "code": "ELECTRIC",
            "weapon": "MG",
            "nikkeClass": "SUPPORTER",
            "cube": null,
            "doll": null
        }
    """.trimIndent()

        val name = "Rapi"

        mockMvc.perform(
            put("/nikke/{name}", name)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors[*]").value("JSON parse error: Cannot deserialize value of type `int` from String \"invalid\": not a valid `int` value"))
    }

    @Test
    @DisplayName("200")
    fun deleteNikkeCase1() {

        val name = "Rapi"

        justRun { nikkeService.deleteNikke(name) }


        mockMvc.perform(
            delete("/nikke/{name}", name)
        )
            .andExpect(status().isOk)

    }

    @Test
    @DisplayName("404")
    fun deleteNikkeCase2() {

        val name = "Rapi"

        every { nikkeService.deleteNikke(name) } throws NikkeNotFoundException(name)

        mockMvc.perform(
            delete("/nikke/{name}", name)
        )
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.message").value("Nikke with name '$name' not found"))

    }

    @Test
    @DisplayName("200")
    fun listNikkesCase1() {

        val nikke1 = Nikke(
            id = 1,
            name = "Test",
            core = 1,
            attraction = 1,
            skill1Level = 1,
            skill2Level = 1,
            burstLevel = 1,
            rarity = Rarity.SSR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.PILGRIM,
            code = Code.ELECTRIC,
            weapon = Weapon.SR,
            nikkeClass = NikkeClass.ATTACKER,
            cube = null,
            doll = null
        )

        val nikke2 = Nikke(
            id = 2,
            name = "Test",
            core = 1,
            attraction = 1,
            skill1Level = 1,
            skill2Level = 1,
            burstLevel = 1,
            rarity = Rarity.SSR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.PILGRIM,
            code = Code.ELECTRIC,
            weapon = Weapon.SR,
            nikkeClass = NikkeClass.ATTACKER,
            cube = null,
            doll = null
        )

        val expectedNikkes = listOf(
            nikke1, nikke2
        )

        val pageable: Pageable = PageRequest.of(0, 2)
        val page: Page<Nikke> = PageImpl(expectedNikkes, pageable, expectedNikkes.size.toLong())

        every { nikkeService.listAllNikke(any()) } returns page

        mockMvc.perform(
            get("/nikke")
                .param("rarity", "SSR")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$._embedded.nikkeList", hasSize<Any>(2)))
            .andExpect(jsonPath("$._embedded.nikkeList[*].name").value(expectedNikkes.map { it.name }))
            .andExpect(jsonPath("$._links.self.href").exists())
    }

//TODO(FIX)

    @Test
    @DisplayName("200")
    fun listNikkesCase2() {

        val expectedNikkes = emptyList<Nikke>()

        val pageable: Pageable = PageRequest.of(0, 2)
        val page: Page<Nikke> = PageImpl(expectedNikkes, pageable, 0)

        every {
            nikkeService.listAllNikke(pageable = any())
        } returns page

        mockMvc.perform(
            get("/nikke")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$._embedded").doesNotExist())
    }

    @Test
    @DisplayName("200")
    fun getNikkesCase1() {

        val nikke = Nikke(
            id = 1,
            name = "Rapi",
            core = 1,
            attraction = 1,
            skill1Level = 1,
            skill2Level = 1,
            burstLevel = 1,
            rarity = Rarity.SSR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.PILGRIM,
            code = Code.ELECTRIC,
            weapon = Weapon.SR,
            nikkeClass = NikkeClass.ATTACKER,
            cube = null,
            doll = null
        )

        val name = "Rapi"
        every { nikkeService.searchNikke(name) } returns nikke

        mockMvc.perform(
            get("/nikke/{name}", name)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value(name))
    }

    @Test
    @DisplayName("404")
    fun getNikkesCase2() {

        val name = "Rapi"

        every { nikkeService.searchNikke(name) } throws NikkeNotFoundException(name)

        mockMvc.perform(
            get("/nikke/{name}", name)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.message").value("Nikke with name '$name' not found"))
    }
}