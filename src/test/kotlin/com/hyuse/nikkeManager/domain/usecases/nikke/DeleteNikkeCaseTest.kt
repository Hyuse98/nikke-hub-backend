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
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class DeleteNikkeCaseTest {

    private val nikkeRepository: NikkeRepository = mock()
    private val deleteNikkeCase = DeleteNikkeCase(nikkeRepository)

    private val id = 1
    private val nikkeName = "Test Nikke"
    val expectedNikke = Nikke.create(
        name = CharacterName.of(nikkeName),
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
    fun `should delete when nikke exist`() {

        whenever(nikkeRepository.existsById(id)).thenReturn(true)

        deleteNikkeCase.execute(id)

        verify(nikkeRepository).delete(id)
    }

    @Test
    fun `should not delete when nikke not exist`() {

        whenever(nikkeRepository.existsById(id)).thenReturn(false)

        val exception = assertThrows<NikkeIdNotFoundException> {
            deleteNikkeCase.execute(id)
        }

        assertThat(exception.message).isEqualTo("Nikke with id $id not found")
    }
}