package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.exceptions.doll.DollNotFoundException
import com.hyuse.nikkeManager.domain.ports.DollRepository
import com.hyuse.nikkeManager.domain.vo.DollLevel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class DeleteDollCaseTest {

    private val dollRepository: DollRepository = mock()
    private val deleteDollCase = DeleteDollCase(dollRepository)

    val id = 1

    val doll = Doll.reconstitute(
        id = id,
        rarity = Rarity.R,
        level = DollLevel.of(1)
    )

    @Test
    fun `should delete a doll when doll exist`() {

        whenever(dollRepository.existById(id)).thenReturn(true)

        deleteDollCase.execute(id)

        verify(dollRepository).existById(id)
        verify(dollRepository).delete(id)
    }

    @Test
    fun `should throw a exception when doll no exist`() {

        whenever(dollRepository.existById(id)).thenReturn(false)

        val exception = assertThrows<DollNotFoundException> {
            deleteDollCase.execute(id)
        }

        assertThat(exception.message).isEqualTo("Doll with ID $id not found")

        verify(dollRepository, times(0)).delete(id)
    }
}