package com.hyuse.nikkeManager.domain.usecases.nikke

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.enums.*
import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeIdNotFoundException
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
import kotlin.test.assertEquals

class EquipNikkeCubeCaseTest {

    private val nikkeRepository: NikkeRepository = mock()
    private val equipNikkeCubeCase = EquipNikkeCubeCase(nikkeRepository)

    private val name = "Mast"
    private fun createNikkeForTest(id: Int, initialCube: Cubes?): Nikke {
        return Nikke.reconstitute(
            id = id,
            name = CharacterName.of(name),
            core = CoreLevel.of(0),
            attraction = AttractionLevel.of(1),
            skill1 = SkillLevel.of(1),
            skill2 = SkillLevel.of(1),
            skillBurst = SkillLevel.of(1),
            rarity = Rarity.SSR,
            ownedStatus = OwnedStatus.OWNED,
            burstType = BurstType.III,
            company = Company.ELYSION,
            code = Code.FIRE,
            weapon = Weapon.AR,
            nikkeClass = NikkeClass.ATTACKER,
            cube = initialCube
        )
    }


    @Test
    fun `should equip cube when nikke is found`() {

        val nikkeId = 1
        val newCube = Cubes.ASSAULT
        val nikke = createNikkeForTest(nikkeId, null)

        whenever(nikkeRepository.findById(nikkeId)).thenReturn(Optional.of(nikke))
        whenever(nikkeRepository.update(any())).thenAnswer { it.getArgument(0) }

        val result = equipNikkeCubeCase.execute(nikkeId, newCube)

        assertThat(result.cube).isEqualTo(newCube)
        assertThat(result.id).isEqualTo(nikkeId)

        verify(nikkeRepository).findById(nikkeId)
        verify(nikkeRepository).update(any())
    }

    @Test
    fun `should not equip cube when nikke not found`() {

        val nikkeId = 1
        val newCube = Cubes.ASSAULT

        whenever(nikkeRepository.findById(nikkeId)).thenReturn(Optional.empty())

        val exception = assertThrows<NikkeIdNotFoundException> {
            equipNikkeCubeCase.execute(nikkeId, newCube)
        }

        assertThat(exception.message).isEqualTo("Nikke with id 1 not found")
    }
}