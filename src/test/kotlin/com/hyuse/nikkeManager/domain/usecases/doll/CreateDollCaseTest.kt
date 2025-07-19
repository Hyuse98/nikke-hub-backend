package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.exceptions.doll.DollAlreadyExistsException
import com.hyuse.nikkeManager.domain.ports.DollRepository
import com.hyuse.nikkeManager.domain.vo.DollLevel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import java.util.*

class CreateDollCaseTest {

    private val dollRepository: DollRepository = mock()
    private val createDollCase = CreateDollCase(dollRepository)

    @Test
    fun `should create doll when one with same properties does not exist`() {

        val rarity = Rarity.SSR
        val level = DollLevel.of(10)
        val newDoll = Doll.create(rarity, level)

        whenever(dollRepository.findByRarityAndLevel(rarity, level)).thenReturn(Optional.empty())
        whenever(dollRepository.save(any<Doll>())).thenReturn(newDoll)

        val result = createDollCase.execute(rarity, 10)

        assertThat(result).isNotNull
        assertThat(result.rarity).isEqualTo(rarity)
        assertThat(result.level).isEqualTo(level)

        verify(dollRepository).findByRarityAndLevel(rarity, level)
        verify(dollRepository).save(any<Doll>())
    }

    @Test
    fun `should throw exception when doll with same properties already exists`() {

        val rarity = Rarity.SR
        val level = DollLevel.of(5)
        val existingDoll = Doll.create(rarity, level)

        whenever(dollRepository.findByRarityAndLevel(rarity, level)).thenReturn(Optional.of(existingDoll))

        val exception = assertThrows<DollAlreadyExistsException> {
            createDollCase.execute(rarity, level.value)
        }

        assertThat(exception.message).isEqualTo("Doll with Rarity $rarity and Level ${level.value} already exists")

        verify(dollRepository, never()).save(any<Doll>())
    }

}