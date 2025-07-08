package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.exceptions.doll.DollNotFoundException
import com.hyuse.nikkeManager.domain.ports.DollRepository
import com.hyuse.nikkeManager.domain.vo.DollLevel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

class GetDollByIdCaseTest {

    private val dollRepository: DollRepository = mock()
    private val getDollByIdCase = GetDollByIdCase(dollRepository)

    private val id = 1

    private val expectedDoll = Doll.reconstitute(
        id = id,
        rarity = Rarity.SR,
        level = DollLevel.of(5)
    )

    @Test
    fun `should find a doll when exist`() {

        whenever(dollRepository.existById(id)).thenReturn(true)
        whenever(dollRepository.findById(id)).thenReturn(Optional.of(expectedDoll))

        val result = getDollByIdCase.execute(id)

        assertThat(result).isEqualTo(expectedDoll)

        verify(dollRepository).existById(id)
        verify(dollRepository).findById(id)
    }

    @Test
    fun `should no find a doll when not exist`() {

        whenever(dollRepository.existById(id)).thenReturn(false)

        val exception = assertThrows<DollNotFoundException> {
            getDollByIdCase.execute(id)
        }

        assertThat(exception.message).isEqualTo("Doll with ID $id not found")

        verify(dollRepository).existById(id)
    }
}