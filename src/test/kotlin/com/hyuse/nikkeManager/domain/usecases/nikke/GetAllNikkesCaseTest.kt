package com.hyuse.nikkeManager.domain.usecases.nikke

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.enums.*
import com.hyuse.nikkeManager.domain.exceptions.CollectionEmptyException
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

class GetAllNikkesCaseTest {

    private val nikkeRepository: NikkeRepository = mock()
    private val getAllNikkesCase = GetAllNikkesCase(nikkeRepository)

    private fun createNikkeForTest(
        id: Int?,
        name: CharacterName,
        core: CoreLevel,
        attraction: AttractionLevel,
        skill1: SkillLevel,
        skill2: SkillLevel,
        skillBurst: SkillLevel,
        rarity: Rarity,
        ownedStatus: OwnedStatus,
        burstType: BurstType,
        company: Company,
        code: Code,
        weapon: Weapon,
        nikkeClass: NikkeClass,
        cube: Cubes?
    ): Nikke {
        return Nikke.reconstitute(
            id = id,
            name = name,
            core = core,
            attraction = attraction,
            skill1 = skill1,
            skill2 = skill2,
            skillBurst = skillBurst,
            rarity = rarity,
            ownedStatus = ownedStatus,
            burstType = burstType,
            company = company,
            code = code,
            weapon = weapon,
            nikkeClass = nikkeClass,
            cube = cube
        )
    }

    @Test
    fun `should return all nikkes when available ate least one`() {

        val nikke1 = createNikkeForTest(
            id = 1,
            name = CharacterName.of("Mast"),
            core = CoreLevel.of(1),
            attraction = AttractionLevel.of(1),
            skill1 = SkillLevel.of(1),
            skill2 = SkillLevel.of(1),
            skillBurst = SkillLevel.of(1),
            rarity = Rarity.SSR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.II,
            company = Company.ELYSION,
            code = Code.FIRE,
            weapon = Weapon.SMG,
            nikkeClass = NikkeClass.SUPPORTER,
            cube = null
        )

        val nikke2 = createNikkeForTest(
            id = 1,
            name = CharacterName.of("Anchor"),
            core = CoreLevel.of(1),
            attraction = AttractionLevel.of(1),
            skill1 = SkillLevel.of(1),
            skill2 = SkillLevel.of(1),
            skillBurst = SkillLevel.of(1),
            rarity = Rarity.SR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.I,
            company = Company.ELYSION,
            code = Code.WATER,
            weapon = Weapon.RL,
            nikkeClass = NikkeClass.SUPPORTER,
            cube = null
        )

        val listOfNikkes = mutableListOf(nikke1, nikke2)

        whenever(nikkeRepository.findAll()).thenReturn(listOfNikkes)

        val result = getAllNikkesCase.execute()

        assertThat(result).isNotNull
        assertThat(result.size).isEqualTo(2)
        assertThat(result).containsExactlyInAnyOrder(nikke1, nikke2)

        verify(nikkeRepository).findAll()
    }

    @Test
    fun `should throw a exception when there no nikkes available`() {

        whenever(nikkeRepository.findAll()).thenReturn(mutableListOf())

        val exception = assertThrows<CollectionEmptyException> {
            getAllNikkesCase.execute()
        }

        assertThat(exception.message).isEqualTo("Collection Empty")
    }
}