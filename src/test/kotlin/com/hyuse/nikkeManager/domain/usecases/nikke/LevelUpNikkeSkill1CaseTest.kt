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
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

class LevelUpNikkeSkill1CaseTest {

    private val nikkeRepository: NikkeRepository = mock()
    private val levelUpNikkeSkill1Case = LevelUpNikkeSkill1Case(nikkeRepository)

    private val id = 1
    private val name = "Mast"
    private val expectedNikke = Nikke.reconstitute(
        id = id,
        name = CharacterName.of(name),
        core = CoreLevel.of(0),
        attraction = AttractionLevel.of(1),
        skill1 = SkillLevel.of(1),
        skill2 = SkillLevel.of(1),
        skillBurst = SkillLevel.of(1),
        rarity = Rarity.SSR,
        ownedStatus = OwnedStatus.NOT_OWNED,
        burstType = BurstType.III,
        company = Company.TETRA,
        code = Code.FIRE,
        weapon = Weapon.AR,
        nikkeClass = NikkeClass.ATTACKER,
        cube = Cubes.ASSAULT
    )

    @Test
    fun `should level up when nikke exist`() {

        whenever(nikkeRepository.findById(id)).thenReturn(Optional.of(expectedNikke))
        whenever(nikkeRepository.update(expectedNikke)).thenReturn(expectedNikke)

        val result = levelUpNikkeSkill1Case.execute(id)

        assertThat(result.skill1.value).isEqualTo(2)

        verify(nikkeRepository).findById(id)
        verify(nikkeRepository).update(expectedNikke)
    }

    @Test
    fun `should throw a exception when nikke not exist`() {

        whenever(nikkeRepository.findById(1)).thenReturn(Optional.empty())

        val exception = assertThrows<NikkeNotFoundException> {
            levelUpNikkeSkill1Case.execute(id)
        }

        assertThat(exception.message).isEqualTo("Nikke with name $id not found")
    }
}