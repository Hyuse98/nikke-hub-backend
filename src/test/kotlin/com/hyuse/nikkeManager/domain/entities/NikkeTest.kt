package com.hyuse.nikkeManager.domain.entities

import com.hyuse.nikkeManager.domain.enums.*
import com.hyuse.nikkeManager.domain.vo.AttractionLevel
import com.hyuse.nikkeManager.domain.vo.CharacterName
import com.hyuse.nikkeManager.domain.vo.CoreLevel
import com.hyuse.nikkeManager.domain.vo.SkillLevel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class NikkeTest {

    private fun createValidNikke(): Nikke {
        return Nikke.create(
            name = CharacterName.of("Rapi"),
            core = CoreLevel.of(0),
            attraction = AttractionLevel.of(1),
            skill1 = SkillLevel.of(1),
            skill2 = SkillLevel.of(1),
            skillBurst = SkillLevel.of(1),
            rarity = Rarity.SR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.ELYSION,
            code = Code.FIRE,
            weapon = Weapon.AR,
            nikkeClass = NikkeClass.ATTACKER
        )
    }

    private fun createWrongNikke(): Nikke {
        return Nikke.create(
            name = CharacterName.of("Neon"),
            core = CoreLevel.of(0),
            attraction = AttractionLevel.of(1),
            skill1 = SkillLevel.of(1),
            skill2 = SkillLevel.of(1),
            skillBurst = SkillLevel.of(1),
            rarity = Rarity.SR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.ELYSION,
            code = Code.FIRE,
            weapon = Weapon.AR,
            nikkeClass = NikkeClass.ATTACKER
        )
    }

    @Nested
    inner class CreateAndValidation {

        @Test
        fun `should create a Nikke with valid parameters`() {

            val nikke = createValidNikke()

            assertNotNull(nikke)
            assertThat("Rapi").isEqualTo(nikke.name.value)
            assertThat(0).isEqualTo(nikke.core.value)
        }

        @Test
        fun `should create a Nikke and validate its initial state`() {

            val nikke = Nikke.reconstitute(
                id = 1,
                name = CharacterName.of("Rapi"),
                core = CoreLevel.of(0),
                attraction = AttractionLevel.of(1),
                skill1 = SkillLevel.of(1),
                skill2 = SkillLevel.of(1),
                skillBurst = SkillLevel.of(1),
                rarity = Rarity.SR,
                ownedStatus = OwnedStatus.NOT_OWNED,
                burstType = BurstType.III,
                company = Company.ELYSION,
                code = Code.FIRE,
                weapon = Weapon.AR,
                nikkeClass = NikkeClass.ATTACKER,
                cube = null
            )

            assertThat(nikke).isNotNull

            assertThat(nikke).usingRecursiveComparison().isEqualTo(
                Nikke.reconstitute(
                    id = 1,
                    name = CharacterName.of("Rapi"),
                    core = CoreLevel.of(0),
                    attraction = AttractionLevel.of(1),
                    skill1 = SkillLevel.of(1),
                    skill2 = SkillLevel.of(1),
                    skillBurst = SkillLevel.of(1),
                    rarity = Rarity.SR,
                    ownedStatus = OwnedStatus.NOT_OWNED,
                    burstType = BurstType.III,
                    company = Company.ELYSION,
                    code = Code.FIRE,
                    weapon = Weapon.AR,
                    nikkeClass = NikkeClass.ATTACKER,
                    cube = null
                )
            )
        }
    }

    @Nested
    inner class BusinessLogic {

        @Test
        fun `should correct wrong nikke information`() {

            val nikke = createWrongNikke()
            val name = "Neon"

            val updatedNikke = nikke.correctBaseData(
                name = CharacterName.of(name),
                rarity = Rarity.SR,
                burstType = BurstType.I,
                company = Company.ELYSION,
                code = Code.FIRE,
                weapon = Weapon.SG,
                nikkeClass = NikkeClass.SUPPORTER
            )

            val expectedNikke = object {
                val name = CharacterName.of("Neon")
                val rarity = Rarity.SR
                val burstType = BurstType.I
                val company = Company.ELYSION
                val code = Code.FIRE
                val weapon = Weapon.SG
                val nikkeClass = NikkeClass.SUPPORTER
            }

            assertThat(updatedNikke)
                .usingRecursiveComparison()
                .ignoringFields("id", "core", "attraction", "skill1", "skill2", "skillBurst", "ownedStatus", "cube")
                .isEqualTo(expectedNikke)
        }

        @Test
        fun `should level up skill1`() {

            val nikke = createValidNikke()
            val initialSkillLevel = nikke.skill1.value

            val updatedNikke = nikke.skill1LevelUp()

            assertThat(initialSkillLevel + 1).isEqualTo(updatedNikke.skill1.value)
        }

        @Test
        fun `should level up skill2`() {

            val nikke = createValidNikke()
            val initialSkillLevel = nikke.skill2.value

            val updatedNikke = nikke.skill2LevelUp()

            assertThat(initialSkillLevel + 1).isEqualTo(updatedNikke.skill2.value)
        }

        @Test
        fun `should level up skillBurst`() {

            val nikke = createValidNikke()
            val initialSkillLevel = nikke.skillBurst.value

            val updatedNikke = nikke.skillBurstLevelUp()

            assertThat(initialSkillLevel + 1).isEqualTo(updatedNikke.skillBurst.value)
        }

        @Test
        fun `should not level up skill1 beyond max level`() {
            val nikke = Nikke.create(
                name = CharacterName.of("Rapi"),
                core = CoreLevel.of(0),
                attraction = AttractionLevel.of(1),
                skill1 = SkillLevel.of(10),
                skill2 = SkillLevel.of(1),
                skillBurst = SkillLevel.of(1),
                rarity = Rarity.SR,
                ownedStatus = OwnedStatus.NOT_OWNED,
                burstType = BurstType.III,
                company = Company.ELYSION,
                code = Code.FIRE,
                weapon = Weapon.AR,
                nikkeClass = NikkeClass.ATTACKER
            )

            val updatedNikke = nikke.skill1LevelUp()

            assertThat(10).isEqualTo(updatedNikke.skill1.value)
        }

        @Test
        fun `should level up core`() {
            val nikke = createValidNikke()
            val initialCoreLevel = nikke.core.value

            val updatedNikke = nikke.coreLevelUp()

            assertThat(initialCoreLevel + 1).isEqualTo(updatedNikke.core.value)
        }

        @Test
        fun `should increase attraction`() {

            val nikke = createValidNikke()
            val initialAttractionLevel = nikke.attraction.value

            val updatedNikke = nikke.attractionIncrement()

            assertThat(initialAttractionLevel + 1).isEqualTo(updatedNikke.attraction.value)
        }

        @Test
        fun `should add a cube on Nikke`() {

            val nikke = createValidNikke()

            val updatedNikke = nikke.updateCube(Cubes.ASSAULT)

            assertThat(updatedNikke.cube).isEqualTo(Cubes.ASSAULT)
        }

        @Test
        fun `should throw exception for invalid name`() {
            assertThrows<IllegalArgumentException> {
                CharacterName.of("")
            }
        }
    }
}