package com.hyuse.nikkeManager.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.hyuse.nikkeManager.dto.NikkeDTO
import com.hyuse.nikkeManager.enums.*
import com.hyuse.nikkeManager.repository.NikkeRepository
import com.hyuse.nikkeManager.service.NikkeService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
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
            .andExpect(jsonPath("$.errors[0]").value("JSON parse error: Cannot deserialize value of type `int` from String \"invalid\": not a valid `int` value"))
    }

    //TODO(Update Test)
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


//TODO(Delete Test)

//TODO(List Nikkes Test)

//TODO(Get Nikke Test)
}