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
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*
import kotlin.test.assertEquals

class GetNikkeByNameCaseTest {

    private val nikkeRepository: NikkeRepository = mock()
    private val getNikkeByNameCase = GetNikkeByNameCase(nikkeRepository)

    val name = "Mast"
    val expectedNikke = Nikke.create(
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
        nikkeClass = NikkeClass.ATTACKER
    )

    @Test
    fun `should return a nikke when exist`(){

        whenever(nikkeRepository.existsByName(name)).thenReturn(true)
        whenever(nikkeRepository.findByName(name)).thenReturn(Optional.of(expectedNikke))

        val result = getNikkeByNameCase.execute(name)

        assertThat(result).isEqualTo(expectedNikke)

        verify(nikkeRepository).existsByName(name)
        verify(nikkeRepository).findByName(name)
    }

    @Test
    fun `should throw a exception when nikke not exist`(){

        whenever(nikkeRepository.existsByName(name)).thenReturn(false)

        val exception = assertThrows<NikkeNotFoundException> {
            getNikkeByNameCase.execute(name)
        }

        assertThat(exception.message).isEqualTo("Nikke with name $name not found")

        verify(nikkeRepository).existsByName(name)
    }



}