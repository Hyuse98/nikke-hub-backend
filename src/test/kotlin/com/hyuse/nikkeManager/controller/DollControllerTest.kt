package com.hyuse.nikkeManager.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.hyuse.nikkeManager.dto.DollDTO
import com.hyuse.nikkeManager.enums.Rarity
import com.hyuse.nikkeManager.exception.DollAlreadyExistsException
import com.hyuse.nikkeManager.model.Doll
import com.hyuse.nikkeManager.repository.DollRepository
import com.hyuse.nikkeManager.service.DollService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(DollController::class)
class DollControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var dollService: DollService

    @MockkBean
    lateinit var dollRepository: DollRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("201")
    fun createDollCase1() {

        val request = DollDTO(
            id = null,
            rarity = Rarity.SSR,
            level = 2
        )

        val response = request.copy(id = 1)

        every { dollService.createDoll(request) } returns response.toModel()

        mockMvc.perform(
            post("/doll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))
    }



}