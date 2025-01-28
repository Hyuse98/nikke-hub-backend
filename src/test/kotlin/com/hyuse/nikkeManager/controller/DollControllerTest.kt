package com.hyuse.nikkeManager.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.hyuse.nikkeManager.dto.DollDTO
import com.hyuse.nikkeManager.enums.Rarity
import com.hyuse.nikkeManager.exception.DollAlreadyExistsException
import com.hyuse.nikkeManager.exception.DollNotFoundException
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

    @Test
    @DisplayName("409")
    fun createDollCase2() {

        val request = DollDTO(
            id = null,
            rarity = Rarity.SSR,
            level = 2
        )

        every { dollService.createDoll(request) } throws DollAlreadyExistsException(request.rarity, request.level)

        mockMvc.perform(
            post("/doll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(request))
        )
            .andExpect(status().isConflict)
            .andExpect(jsonPath("$.message").value("Doll Rarity: '${request.rarity}' Level: '${request.level}' already exists"))
    }

    @Test
    @DisplayName("200")
    fun getListDollsCase1() {

        val doll1 = Doll(
            id = 1,
            rarity = Rarity.SSR,
            level = 5
        )

        val doll2 = Doll(
            id = 2,
            rarity = Rarity.SR,
            level = 15
        )

        val expectDolls = listOf(
            doll1, doll2
        )

        every { dollService.getListDolls() } returns expectDolls

        mockMvc.perform(
            get("/doll")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$._embedded.dollList", hasSize<Any>(2)))
            .andExpect(jsonPath("$._embedded.dollList[0].rarity").value(doll1.rarity.name))
            .andExpect(jsonPath("$._embedded.dollList[0].level").value(doll1.level))
            .andExpect(jsonPath("$._embedded.dollList[1].rarity").value(doll2.rarity.name))
            .andExpect(jsonPath("$._embedded.dollList[1].level").value(doll2.level))
            .andExpect(jsonPath("$._embedded.dollList[0]._links.self.href").value("http://localhost/doll/1"))
            .andExpect(jsonPath("$._links.self.href").value("http://localhost/doll"))

    }

    @Test
    @DisplayName("200")
    fun getListDollsCase2() {
        every { dollService.getListDolls() } returns emptyList()

        mockMvc.perform(
            get("/doll")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$._embedded.dollList").doesNotExist()) // Lista não está presente
            .andExpect(jsonPath("$._links.self.href").value("http://localhost/doll")) // Link self está presente
    }


    @Test
    @DisplayName("200")
    fun getDollsCase1ByRarityAndLevel(){

        val doll= Doll(
            id = 1,
            rarity = Rarity.SR,
            level = 5
        )

        every { dollService.getDollByRarityAndLevel(Rarity.SR, 5) } returns doll

        mockMvc.perform(
            get("/doll/search?rarity=SR&level=5")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(doll.id))
    }

    @Test
    @DisplayName("404")
    fun getDollsCase2ByRarityAndLevel(){

        val rarity = Rarity.SR
        val level = 5

        every { dollService.getDollByRarityAndLevel(rarity, level) } throws DollNotFoundException(rarity, level)

        mockMvc.perform(
            get("/doll/search?rarity=SR&level=5")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.message").value("Doll Rarity: '$rarity' Level: '$level' not found"))
    }
}