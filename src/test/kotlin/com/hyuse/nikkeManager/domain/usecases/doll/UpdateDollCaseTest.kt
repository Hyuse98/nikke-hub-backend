package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.exceptions.doll.DollNotFoundException
import com.hyuse.nikkeManager.domain.ports.DollRepository
import com.hyuse.nikkeManager.domain.vo.DollLevel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

class UpdateDollCaseTest {

    private val dollRepository: DollRepository = mock()
    private val updateDollCase = UpdateDollCase(dollRepository)

    val id = 1
    private val doll = Doll.reconstitute(
        id = id,
        rarity = Rarity.SR,
        level = DollLevel.of(5)
    )

    @Test
    fun `should update doll when doll exist`() {

        whenever(dollRepository.findById(id)).thenReturn(Optional.of(doll))
        whenever(dollRepository.update(any(), any())).thenAnswer { invocation ->
            invocation.getArgument(1)
        }

        val result = updateDollCase.execute(id, Rarity.SR, 6)

        assertThat(result).isNotNull
        assertThat(result.rarity).isEqualTo(Rarity.SR)
        assertThat(result.level.value).isEqualTo(6)

        verify(dollRepository).findById(id)
        verify(dollRepository).update(any(), any())
    }

    @Test
    fun `should throw a exception when doll not exist`() {

        whenever(dollRepository.findById(id)).thenReturn(Optional.empty())

        val exception = assertThrows<DollNotFoundException> {
            updateDollCase.execute(id, Rarity.SR, 6)
        }

        assertThat(exception.message).isEqualTo("Doll with ID $id not found")

        verify(dollRepository).findById(id)
    }
}