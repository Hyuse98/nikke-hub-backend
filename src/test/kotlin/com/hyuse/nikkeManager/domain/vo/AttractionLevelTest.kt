package com.hyuse.nikkeManager.domain.vo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AttractionLevelTest {

    @Nested
    inner class CreationAndValidation {

        @Test
        fun `should create attraction successfully with valid level`() {

            val attractionLevel = AttractionLevel.of(20)

            assertThat(attractionLevel.value).isEqualTo(20)
        }

        @Test
        fun `should not create attraction when level is bellow minimum level`() {

            val exception = assertThrows<IllegalArgumentException> {
                AttractionLevel.of(0)
            }

            assertThat(exception.message).isEqualTo("Attraction min level must be 1")
        }

        @Test
        fun `should not create attraction when level is above maximum level`() {

            val exception = assertThrows<IllegalArgumentException> {
                AttractionLevel.of(31)
            }

            assertThat(exception.message).isEqualTo("Attraction max leve must be 30")
        }
    }

    @Nested
    inner class BusinessLogic {

        @Test
        fun `should level up when level is bellow maximum level`() {

            val attractionLevel = AttractionLevel.of(19)

            val newAttractionLevel = attractionLevel.increment()

            assertThat(newAttractionLevel.value).isEqualTo(20)
        }

        @Test
        fun `should not level up when level is above maximum level`() {

            val attractionLevel = AttractionLevel.of(30)

            val newAttractionLevel = attractionLevel.increment()

            assertThat(newAttractionLevel.value).isEqualTo(30)
        }
    }
}