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
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

class CorrectNikkeBaseDataCaseTest {

    private val nikkeRepository: NikkeRepository = mock()
    private val correctNikkeBaseDataCase = CorrectNikkeBaseDataCase(nikkeRepository)

    private fun createNikkeForTest(): Nikke {
        return Nikke.create(
            name = CharacterName.of("Mast"),
            core = CoreLevel.of(0),
            attraction = AttractionLevel.of(1),
            skill1 = SkillLevel.of(1),
            skill2 = SkillLevel.of(1),
            skillBurst = SkillLevel.of(1),
            rarity = Rarity.SR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.TETRA,
            code = Code.WIND,
            weapon = Weapon.AR,
            nikkeClass = NikkeClass.ATTACKER
        )
    }

    @Test
    fun `should update metadata successfully`() {

        val nikke = createNikkeForTest()

        whenever(nikkeRepository.findById(any())).thenReturn(Optional.of(nikke))
        whenever(nikkeRepository.update(any())).thenAnswer { it.getArgument(0) }

        val updatedNikke = correctNikkeBaseDataCase.execute(
            id = 1,
            name = "Mast",
            rarity = Rarity.SSR,
            burstType = BurstType.II,
            company = Company.ELYSION,
            code = Code.FIRE,
            weapon = Weapon.SMG,
            nikkeClass = NikkeClass.SUPPORTER
        )

        val expectedNikke = object {
            val name = CharacterName.of("Mast")
            val rarity = Rarity.SSR
            val burstType = BurstType.II
            val company = Company.ELYSION
            val code = Code.FIRE
            val weapon = Weapon.SMG
            val nikkeClass = NikkeClass.SUPPORTER
        }

        assertThat(updatedNikke)
            .usingRecursiveComparison()
            .ignoringFields("id", "core", "attraction", "skill1", "skill2", "skillBurst", "ownedStatus", "cube")
            .isEqualTo(expectedNikke)

        verify(nikkeRepository).findById(any())
        verify(nikkeRepository).update(any())
    }

    @Test
    fun `should no update metadata when nikke not found`() {

        whenever(nikkeRepository.findByName(any())).thenReturn(Optional.empty())

        val exception = assertThrows<NikkeNotFoundException> {
            correctNikkeBaseDataCase.execute(
                id = 1,
                name = "Anchor",
                rarity = Rarity.SR,
                burstType = BurstType.I,
                company = Company.ELYSION,
                code = Code.WATER,
                weapon = Weapon.RL,
                nikkeClass = NikkeClass.SUPPORTER
            )
        }

        assertThat(exception.message).isEqualTo("Nikke with name Anchor not found")

        verify(nikkeRepository).findById(any())
    }

}