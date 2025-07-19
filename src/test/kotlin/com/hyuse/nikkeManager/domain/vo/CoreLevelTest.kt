package com.hyuse.nikkeManager.domain.vo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CoreLevelTest {

    @Nested
    inner class CreationAndValidation {

        @Test
        fun `should create core level when value is valid level`() {

            val coreLevel = CoreLevel.of(0)

            assertThat(coreLevel.value).isEqualTo(0)
        }

        @Test
        fun `should not create core level when value is bellow minimum level`() {

            val exception = assertThrows<IllegalArgumentException> {
                CoreLevel.of(-1)
            }

            assertThat(exception.message).isEqualTo("Core min level must be 0")
        }

        @Test
        fun `should not create core level when value is above maximum level`() {

            val exception = assertThrows<IllegalArgumentException> {
                CoreLevel.of(12)
            }

            assertThat(exception.message).isEqualTo("Core max level must be 10")
        }
    }

    @Nested
    inner class BusinessLogic {

        @Test
        fun `should level up when level is bellow maximum level`() {

            val validLevel = CoreLevel.of(1)

            val newCoreLevel = validLevel.levelUp()

            assertThat(newCoreLevel.value).isEqualTo(2)
        }

        @Test
        fun `should not level up when level is above maximum level`() {

            val invalidLevel = CoreLevel.of(10)

            val newCoreLevel = invalidLevel.levelUp()

            assertThat(newCoreLevel.value).isEqualTo(10)
        }
    }
}