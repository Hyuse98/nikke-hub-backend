package com.hyuse.nikkeManager.domain.usecases.nikke

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.enums.*
import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeNotFoundException
import com.hyuse.nikkeManager.domain.ports.NikkeRepository
import com.hyuse.nikkeManager.domain.vo.AttractionLevel
import com.hyuse.nikkeManager.domain.vo.CharacterName
import com.hyuse.nikkeManager.domain.vo.CoreLevel
import com.hyuse.nikkeManager.domain.vo.SkillLevel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

class ChangeNikkeOwnershipStatusCaseTest {

    private val nikkeRepository: NikkeRepository = mock()
    private val changeNikkeOwnershipStatusCase = ChangeNikkeOwnershipStatusCase(nikkeRepository)

    private fun createNikkeForTest(): Nikke {
        return Nikke.reconstitute(
            id = 1,
            name = CharacterName.of("Mast"),
            core = CoreLevel.of(0),
            attraction = AttractionLevel.of(1),
            skill1 = SkillLevel.of(1),
            skill2 = SkillLevel.of(1),
            skillBurst = SkillLevel.of(1),
            rarity = Rarity.SSR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.ELYSION,
            code = Code.FIRE,
            weapon = Weapon.AR,
            nikkeClass = NikkeClass.ATTACKER,
            cube = null
        )
    }

    @Test
    fun `should update owned status to owned`() {

        val nikkeId = 1
        val nikke = createNikkeForTest()

        whenever(nikkeRepository.findById(any())).thenReturn(Optional.of(nikke))
        whenever(nikkeRepository.update(any())).thenAnswer { it.getArgument(0) }

        val updatedNikke = changeNikkeOwnershipStatusCase.execute(nikkeId)

        assertThat(updatedNikke.ownedStatus).isEqualTo(OwnedStatus.OWNED)

        verify(nikkeRepository).findById(nikkeId)
        verify(nikkeRepository).update(any())
    }

    @Test
    fun `should throw NikkeNotFoundException when nikke is not found`() {

        val nikkeId = 2

        whenever(nikkeRepository.findById(nikkeId)).thenReturn(Optional.empty())

        val exception =
            org.junit.jupiter.api.assertThrows<NikkeNotFoundException> {
                changeNikkeOwnershipStatusCase.execute(nikkeId)
            }

        assertThat(exception.message).isEqualTo("Nikke with id $nikkeId not found")

        verify(nikkeRepository).findById(nikkeId)
        verify(nikkeRepository, org.mockito.kotlin.never()).update(any())
    }
}