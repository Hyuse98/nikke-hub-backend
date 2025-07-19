package com.hyuse.nikkeManager.domain.vo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SkillLevelTest {

    @Nested
    inner class CreationAndValidation {

        @Test
        fun `should create SkillLevel successfully with valid level`() {

            val validLevel = SkillLevel.of(10)

            assertThat(validLevel.value).isEqualTo(10)
        }

        @Test
        fun `should throw IllegalArgumentException when level is below minimum`() {

            val invalidLevel = 0

            val exception = assertThrows<IllegalArgumentException> {
                SkillLevel.of(invalidLevel)
            }

            assertThat("Skill min level must be 1").isEqualTo(exception.message)
        }

        @Test
        fun `should throw IllegalArgumentException when level is above maximum`() {

            val invalidLevel = 20

            val exception = assertThrows<IllegalArgumentException> {
                SkillLevel.of(invalidLevel)
            }

            assertThat("Skill max level must be 10").isEqualTo(exception.message)
        }
    }

    @Nested
    inner class BusinessLogic {
        @Test
        fun `should level up when bellow max level`() {

            val skillLevel = SkillLevel.of(8)
            val leveledSkill = skillLevel.levelUp()

            assertThat(leveledSkill.value).isEqualTo(9)
        }

        @Test
        fun `should not level up when above max level`() {

            val skillLevel = SkillLevel.of(10)
            val leveledSkill = skillLevel.levelUp()

            assertThat(leveledSkill.value).isEqualTo(10)
        }

    }
}