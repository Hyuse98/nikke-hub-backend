package com.hyuse.nikkeManager.infrastructure.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.enums.*
import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeAlreadyExistsException
import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeNotFoundException
import com.hyuse.nikkeManager.domain.usecases.nikke.*
import com.hyuse.nikkeManager.domain.vo.AttractionLevel
import com.hyuse.nikkeManager.domain.vo.CharacterName
import com.hyuse.nikkeManager.domain.vo.CoreLevel
import com.hyuse.nikkeManager.domain.vo.SkillLevel
import com.hyuse.nikkeManager.infrastructure.web.dto.NikkeDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(NikkeController::class)
@ActiveProfiles("test")
@TestPropertySource(properties = ["spring.cloud.config.enabled=false"])
class NikkeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var createNikkeCase: CreateNikkeCase

    @MockkBean
    private lateinit var correctNikkeBaseDataCase: CorrectNikkeBaseDataCase

    @MockkBean
    private lateinit var deleteNikkeCase: DeleteNikkeCase

    @MockkBean
    private lateinit var getNikkeByNameCase: GetNikkeByNameCase

    @MockkBean
    private lateinit var getAllNikkesCase: GetAllNikkesCase

    private fun createNikkeForTest(name: String): Nikke {
        return Nikke.create(
            name = CharacterName.of(name),
            core = CoreLevel.of(0),
            attraction = AttractionLevel.of(1),
            skill1 = SkillLevel.of(1),
            skill2 = SkillLevel.of(1),
            skillBurst = SkillLevel.of(1),
            rarity = Rarity.SSR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.TETRA,
            code = Code.FIRE,
            weapon = Weapon.AR,
            nikkeClass = NikkeClass.ATTACKER
        )
    }

    private fun createNikkeDTO(): NikkeDTO {
        return NikkeDTO(
            id = null,
            name = "Test Nikke",
            core = 0,
            attraction = 1,
            skill1 = 1,
            skill2 = 1,
            skillBurst = 1,
            rarity = Rarity.SSR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.TETRA,
            code = Code.FIRE,
            weapon = Weapon.AR,
            nikkeClass = NikkeClass.ATTACKER,
            cube = null
        )
    }

    @Nested
    inner class Creation {

        @Test
        fun `should create nikke and return 201 Created`() {
            val nikkeDTO = createNikkeDTO()
            val nikke = createNikkeForTest("Test Nikke")

            every {
                createNikkeCase.execute(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns nikke

            mockMvc.perform(
                post("/api/nikke")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(nikkeDTO))
            )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.name.value").value("Test Nikke"))
        }

        @Test
        fun `should return 409 Conflict when creating a nikke that already exists`() {
            val name = "Test Nikke"
            val nikkeDTO = createNikkeDTO()

            every {
                createNikkeCase.execute(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } throws NikkeAlreadyExistsException(name)

            mockMvc.perform(
                post("/api/nikke")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(nikkeDTO))
            )
                .andExpect(status().isConflict)
        }

        @Test
        fun `should return 400 Bad Request when creating a nikke with an invalid name`() {

            val name = ""
            val invalidNikkeDTO = createNikkeDTO().copy(name = name)

            every {
                createNikkeCase.execute(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } throws IllegalArgumentException()

            mockMvc.perform(
                post("/api/nikke")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidNikkeDTO))
            )
                .andExpect(status().isBadRequest)
        }
    }

    @Nested
    inner class Update {

        @Test
        fun `should return 200 OK`() {

            val id = 1
            val nikkeDTO = createNikkeDTO().copy(id = id)
            val nikke = createNikkeForTest("Test Nikke")

            every {
                correctNikkeBaseDataCase.execute(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns nikke

            mockMvc.perform(
                put("/api/nikke/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(nikkeDTO))

            )
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name.value").value("Test Nikke"))
        }

        @Test
        fun `should return 404 Not Found`() {

            val id = 1
            val name = "Test Nikke"
            val nikkeDTO = createNikkeDTO().copy(id = id)

            every {
                correctNikkeBaseDataCase.execute(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } throws NikkeNotFoundException(name)

            mockMvc.perform(
                put("/api/nikke/{id}", 2)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(nikkeDTO))

            )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
        }
    }

    @Nested
    inner class Get {

        @Test
        fun `should get nikke by name and return 200 OK`() {
            val nikkeName = "Rapi"
            val nikke = createNikkeForTest(nikkeName)
            every { getNikkeByNameCase.execute(nikkeName) } returns nikke

            mockMvc.perform(get("/api/nikke/{name}", nikkeName))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.name.value").value(nikkeName))
//                .andDo(MockMvcResultHandlers.print())
        }

        @Test
        fun `should not get nikke by name and return 404 Not Found`() {
            val nikkeName = "Rapi"
            every { getNikkeByNameCase.execute(nikkeName) } throws NikkeNotFoundException(nikkeName)

            mockMvc.perform(get("/api/nikke/{name}", nikkeName))
                .andExpect(status().isNotFound)
                .andExpect(content().contentType("application/json"))
        }
    }

    @Nested
    inner class List {

        @Test
        fun `should list all nikkes and return 200 OK`() {
            val nikkes = listOf(createNikkeForTest("Rapi"), createNikkeForTest("Anis"))
            every { getAllNikkesCase.execute() } returns nikkes

            mockMvc.perform(get("/api/nikke"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
        }

        @Test
        fun `should empty list of nikkes and return 200 OK`() {
            val nikkes = emptyList<Nikke>()
            every { getAllNikkesCase.execute() } returns nikkes

            mockMvc.perform(get("/api/nikke"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0))
        }
    }

    @Nested
    inner class Delete {

        @Test
        fun `should delete nikke and return 200 OK`() {
            val nikkeId = 1
            every { deleteNikkeCase.execute(nikkeId) } returns Unit

            mockMvc.perform(delete("/api/nikke/{id}", nikkeId))
                .andExpect(status().isOk)
        }

        @Test
        fun `should return 404 Not Found when nikke not exist`() {
            val nikkeId = 1
            every { deleteNikkeCase.execute(nikkeId) } throws NikkeNotFoundException(nikkeId)

            mockMvc.perform(delete("/api/nikke/{id}", nikkeId))
                .andExpect(status().isNotFound)
        }
    }
}
