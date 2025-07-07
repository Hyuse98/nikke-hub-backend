package com.hyuse.nikkeManager.domain.usecases.nikke

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.enums.*
import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeAlreadyExistsException
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

class CreateNikkeCaseTest {

    private val nikkeRepository: NikkeRepository = mock()
    private val createNikkeCase = CreateNikkeCase(nikkeRepository)

    @Test
    fun `should create nikke successfully when name does not exist`() {

        val nikkeName = "Mast"
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

        whenever(nikkeRepository.existsByName(nikkeName)).thenReturn(false)
        whenever(nikkeRepository.save(any<Nikke>())).thenReturn(expectedNikke)

        val result = createNikkeCase.execute(
            name = nikkeName,
            core = 0,
            attraction = 1,
            skill1 = 1,
            skill2 = 1,
            skillBurst = 1,
            rarity = Rarity.SSR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.TETRA,
            code = Code.FIRE,
            weapon = Weapon.AR,
            nikkeClass = NikkeClass.ATTACKER
        )

        assertThat(expectedNikke).isEqualTo(result)

        verify(nikkeRepository).existsByName(nikkeName)
        verify(nikkeRepository).save(any<Nikke>())
    }

    @Test
    fun `should throw NikkeAlreadyExistsException when name already exists`() {

        val nikkeName = "Existing Nikke"
        whenever(nikkeRepository.existsByName(nikkeName)).thenReturn(true)

        val exception = assertThrows<NikkeAlreadyExistsException> {
            createNikkeCase.execute(
                name = nikkeName,
                core = 0,
                attraction = 1,
                skill1 = 1,
                skill2 = 1,
                skillBurst = 1,
                rarity = Rarity.SSR,
                ownedStatus = OwnedStatus.NOT_OWNED,
                burstType = BurstType.III,
                company = Company.TETRA,
                code = Code.FIRE,
                weapon = Weapon.AR,
                nikkeClass = NikkeClass.ATTACKER
            )
        }

        assertThat(exception.message).isEqualTo("Nikke with name $nikkeName already exists")

        verify(nikkeRepository).existsByName(nikkeName)
    }
}