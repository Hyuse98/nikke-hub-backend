package com.hyuse.nikkeManager.service

import com.hyuse.nikkeManager.dto.DollDTO
import com.hyuse.nikkeManager.enums.Rarity
import com.hyuse.nikkeManager.exceptions.DollAlreadyExistsException
import com.hyuse.nikkeManager.exceptions.DollNotFoundException
import com.hyuse.nikkeManager.model.Doll
import com.hyuse.nikkeManager.repository.DollRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class DollServiceTest {

    @InjectMocks
    lateinit var dollService: DollService

    @Mock
    lateinit var dollRepository: DollRepository

    @Test
    @DisplayName("Should create doll when it doesn't exist")

    fun createDollCase1() {

        val dollDTO = DollDTO(
            id = null,
            rarity = Rarity.R,
            level = 5
        )

        whenever(dollRepository.findByRarityAndLevel(Rarity.R, 5)).thenReturn(null)
        whenever(dollRepository.save(isA<Doll>())).thenReturn(dollDTO.toModel())

        val result = dollService.createDoll(dollDTO)

        assertThat(result!!).isNotNull
        assertThat(dollDTO.toModel().rarity).isEqualTo(result.rarity)
        assertThat(dollDTO.toModel().level).isEqualTo(result.level)

        verify(dollRepository).findByRarityAndLevel(dollDTO.rarity, dollDTO.level)
        verify(dollRepository).save(isA<Doll>())
    }

    @Test
    @DisplayName("Should not create doll when it exist")
    fun createDollCase2() {

        val dollDTO = DollDTO(
            id = null,
            rarity = Rarity.R,
            level = 5
        )

        whenever(dollRepository.findByRarityAndLevel(Rarity.R, 5)).thenReturn(dollDTO.toModel())

        val exception = assertThrows<DollAlreadyExistsException> {
            dollService.createDoll(dollDTO)
        }
        assertThat(exception.message).isEqualTo("Doll Rarity: '${dollDTO.rarity}' Level: '${dollDTO.level}' already exists")

        verify(dollRepository).findByRarityAndLevel(dollDTO.rarity, dollDTO.level)
    }

    @Test
    @DisplayName("Should find a doll")
    fun getDollByRarityAndLevelCase1() {

        val dollDTO = DollDTO(
            id = null,
            rarity = Rarity.SR,
            level = 5
        )

        whenever(dollRepository.findByRarityAndLevel(Rarity.SR, 5)).thenReturn(dollDTO.toModel())

        val result = dollService.getDollByRarityAndLevel(Rarity.SR, 5)

        assertThat(result!!).isNotNull
        assertThat(dollDTO.toModel().rarity).isEqualTo(result.rarity)
        assertThat(dollDTO.toModel().level).isEqualTo(result.level)

        verify(dollRepository).findByRarityAndLevel(Rarity.SR, 5)
    }

    @Test
    @DisplayName("Should not find a doll")
    fun getDollByRarityAndLevelCase2() {

        whenever(dollRepository.findByRarityAndLevel(Rarity.SR, 5)).thenReturn(null)

        val exception = assertThrows<DollNotFoundException> {
            dollService.getDollByRarityAndLevel(Rarity.SR, 5)
        }

        assertThat(exception.message).isEqualTo("Doll Rarity: 'SR' Level: '5' not found")

        verify(dollRepository).findByRarityAndLevel(Rarity.SR, 5)

    }

    @Test
    @DisplayName("Should list all dolls")
    fun getListDollsCase1() {

        val doll1 = Doll(
            id = 1,
            rarity = Rarity.SSR,
            level = 0
        )
        val doll2 = Doll(
            id = 2,
            rarity = Rarity.SSR,
            level = 0
        )
        val doll3 = Doll(
            id = 3,
            rarity = Rarity.SSR,
            level = 0
        )

        val expectedDoll = listOf(doll1, doll2, doll3)

        val pageable: Pageable = PageRequest.of(0, 3)
        val page: Page<Doll> = PageImpl(expectedDoll, pageable, expectedDoll.size.toLong())

        whenever(dollRepository.findAll(any<Pageable>())).thenReturn(page)

        val result = dollService.getListDolls(pageable)

        verify(dollRepository).findAll(any<Pageable>())

        assertEquals(expectedDoll, result.content)
    }

    @Test
    @DisplayName("Should delete a doll by id")
    fun deleteDollByIdCase1(){

        val doll1 = Doll(
            id = 1,
            rarity = Rarity.SSR,
            level = 0
        )

        whenever(dollRepository.findDollById(1)).thenReturn(doll1)

        dollService.deleteDollById(1)

        verify(dollRepository, times(1)).findDollById(1)
    }

    @Test
    @DisplayName("Should not delete a doll by id due missing")
    fun deleteDollByIdCase2(){

        whenever(dollRepository.findDollById(1)).thenReturn(null)

        val exception = assertThrows<DollNotFoundException> {
            dollService.deleteDollById(1)
        }

        assertThat(exception.message).isEqualTo("Doll with ID '1' not found")
    }
}