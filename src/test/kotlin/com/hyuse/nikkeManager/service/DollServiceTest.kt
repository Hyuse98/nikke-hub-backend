package com.hyuse.nikkeManager.service

import com.hyuse.nikkeManager.dto.DollDTO
import com.hyuse.nikkeManager.enums.Rarity
import com.hyuse.nikkeManager.model.Doll
import com.hyuse.nikkeManager.repository.DollRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.isA
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
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
    //TODO(fix exception later)
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
    //TODO(fix exception later)
    @Test
    @DisplayName("Should not create doll when it exist")
    fun createDollCase2() {
        val dollDTO = DollDTO(
            id = null,
            rarity = Rarity.R,
            level = 5
        )

        whenever(dollRepository.findByRarityAndLevel(Rarity.R, 5)).thenReturn(dollDTO.toModel())

        val exception = assertThrows<IllegalStateException> {
            dollService.createDoll(dollDTO)
        }
        assertThat(exception.message).isEqualTo("Doll already registered for rarity ${dollDTO.rarity} and level ${dollDTO.level}")

        verify(dollRepository).findByRarityAndLevel(dollDTO.rarity, dollDTO.level)
    }
}