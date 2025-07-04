package com.hyuse.nikkeManager.domain.vo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DollLevelTest {

    @Nested
    inner class CreationAndValidation {

        @Test
        fun `should create DollLevel successfully with valid level`() {

            val validLevel = DollLevel.of(10)

            assertThat(validLevel.value).isEqualTo(10)
        }

        @Test
        fun `should throw IllegalArgumentException when level is bellow minimum`() {

            val exception = assertThrows<IllegalArgumentException> {
                DollLevel.of(-1)
            }

            assertThat(exception.message).isEqualTo("Doll min level must be 0")
        }

        @Test
        fun `should throw IllegalArgumentException when level is above maximum`() {

            val exception = assertThrows<IllegalArgumentException> {
                DollLevel.of(16)
            }

            assertThat(exception.message).isEqualTo("Doll max level must be 15")
        }
    }

    @Nested
    inner class BusinessLogic {
        @Test
        fun `should level up when level is bellow maximum level`() {

            val level = DollLevel.of(10)

            val newLevel = level.levelUp()

            assertThat(newLevel.value).isEqualTo(11)
        }

        @Test
        fun `should not level up when level is above maximum level`() {

            val level = DollLevel.of(15)

            val newLevel = level.levelUp()

            assertThat(newLevel.value).isEqualTo(15)
        }
    }
}