package com.hyuse.nikkeManager.infrastructure.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.exceptions.doll.DollAlreadyExistsException
import com.hyuse.nikkeManager.domain.exceptions.doll.DollNotFoundException
import com.hyuse.nikkeManager.domain.usecases.doll.*
import com.hyuse.nikkeManager.domain.vo.DollLevel
import com.hyuse.nikkeManager.infrastructure.web.dto.DollDTO
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(DollController::class)
@ActiveProfiles("test")
@TestPropertySource(properties = ["spring.cloud.config.enabled=false"])
class DollControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var createDollCase: CreateDollCase

    @MockkBean
    private lateinit var updateDollCase: UpdateDollCase

    @MockkBean
    private lateinit var deleteDollCase: DeleteDollCase

    @MockkBean
    private lateinit var getDollByIdCase: GetDollByIdCase

    @MockkBean
    private lateinit var getAllDollsCase: GetAllDollsCase

    private fun createDollForTest(rarity: Rarity, level: Int): Doll {
        return Doll.create(
            rarity = rarity,
            level = DollLevel.of(level)
        )
    }

    private fun createDollDTO(rarity: Rarity, level: Int): DollDTO {
        return DollDTO(
            id = 1,
            rarity = rarity,
            level = level
        )
    }

    @Nested
    inner class Creation {

        @Test
        fun `201 Created`() {

            val doll = createDollForTest(Rarity.SR, 1)
            val dollDTO = createDollDTO(Rarity.SR, 1)

            every {
                createDollCase.execute(
                    any(),
                    any()
                )
            } returns doll

            mockMvc.perform(
                post("/api/doll")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dollDTO))
            )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.rarity").value("SR"))
                .andExpect(jsonPath("$.level.value").value(1))

        }

        @Test
        fun `409 Conflict`() {

            val dollDTO = createDollDTO(Rarity.SR, 1)

            every {
                createDollCase.execute(
                    any(),
                    any()
                )
            } throws DollAlreadyExistsException(dollDTO.rarity, dollDTO.level)

            mockMvc.perform(
                post("/api/doll")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dollDTO))
            )
                .andExpect(status().isConflict)

        }

        @Test
        fun `400 Bad Request`() {

            val dollDTO = createDollDTO(Rarity.SR, 30)

            every {
                createDollCase.execute(
                    any(),
                    any()
                )
            } throws IllegalArgumentException()

            mockMvc.perform(
                post("/api/doll")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dollDTO))
            )
                .andExpect(status().isBadRequest)

        }

    }

    @Nested
    inner class Update {

        @Test
        fun `200 OK`() {

            val id = 1
            val doll = createDollForTest(Rarity.SR, 1)
            val dollDTO = createDollDTO(Rarity.SR, 1).copy(id = id)

            every {
                updateDollCase.execute(
                    any(),
                    any(),
                    any()
                )
            } returns doll

            mockMvc.perform(
                put("/api/doll")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dollDTO))
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.rarity").value("SR"))
                .andExpect(jsonPath("$.level.value").value(1))

        }

        @Test
        fun `404 Not Found`() {

            val id = 1
            val dollDTO = createDollDTO(Rarity.SR, 1).copy(id = id)

            every {
                updateDollCase.execute(
                    any(),
                    any(),
                    any()
                )
            } throws DollNotFoundException(id)

            mockMvc.perform(
                put("/api/doll")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dollDTO))
            )
                .andExpect(status().isNotFound)

        }
    }

    @Nested
    inner class Get {

        @Test
        fun `200 OK`() {

            val id = 1
            val doll = createDollForTest(Rarity.SR, 1)

            every {
                getDollByIdCase.execute(1)
            } returns doll

            mockMvc.perform(get("/api/doll/{id}", id))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.rarity").value("SR"))
                .andExpect(jsonPath("$.level.value").value(1))
        }

        @Test
        fun `404 Not Found`() {

            val id = 1

            every {
                getDollByIdCase.execute(1)
            } throws DollNotFoundException(id)

            mockMvc.perform(get("/api/doll/{id}", id))
                .andExpect(status().isNotFound)
        }

    }

    @Nested
    inner class List {

        @Test
        fun `200 OK`() {

            val dolls = listOf(createDollForTest(Rarity.SR, 1), createDollForTest(Rarity.R, 5))

            every { getAllDollsCase.execute() } returns dolls

            mockMvc.perform(get("/api/doll"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.length()").value(2))

        }

        @Test
        fun `Empty 200 OK`() {

            val dolls = emptyList<Doll>()

            every { getAllDollsCase.execute() } returns dolls

            mockMvc.perform(get("/api/doll"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.length()").value(0))

        }
    }

    @Nested
    inner class Delete {

        @Test
        fun `200 OK`() {

            val id = 1

            every { deleteDollCase.execute(id) } returns Unit

            mockMvc.perform(delete("/api/doll/{id}", id))
                .andExpect(status().isOk)

        }

        @Test
        fun `404 Not Found`() {

            val id = 1

            every { deleteDollCase.execute(id) } throws DollNotFoundException(id)

            mockMvc.perform(delete("/api/doll/{id}", id))
                .andExpect(status().isNotFound)

        }

    }
}
