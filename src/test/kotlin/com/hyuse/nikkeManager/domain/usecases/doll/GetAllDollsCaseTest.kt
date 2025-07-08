package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.exceptions.CollectionEmptyException
import com.hyuse.nikkeManager.domain.ports.DollRepository
import com.hyuse.nikkeManager.domain.vo.DollLevel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetAllDollsCaseTest {

    private val dollRepository: DollRepository = mock()
    private val getAllNikkesCase = GetAllDollsCase(dollRepository)

    private val doll1 = Doll.reconstitute(
        id = 1,
        rarity = Rarity.R,
        level = DollLevel.of(15)
    )

    private val doll2 = Doll.reconstitute(
        id = 2,
        rarity = Rarity.SR,
        level = DollLevel.of(5)
    )

    val dollsList = mutableListOf(doll1, doll2)

    @Test
    fun `should return all dolls when have doll available`(){

        whenever(dollRepository.findAll()).thenReturn(dollsList)

        val result = getAllNikkesCase.execute()

        assertThat(result).containsExactlyInAnyOrder(doll1, doll2)

        verify(dollRepository).findAll()
    }

    @Test
    fun `should throw a exception when theres no one available`(){

        whenever(dollRepository.findAll()).thenReturn(mutableListOf())

        val exception = assertThrows<CollectionEmptyException> {
            getAllNikkesCase.execute()
        }

        assertThat(exception.message).isEqualTo("Collection Empty")

    }
}