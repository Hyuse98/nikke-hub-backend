package com.hyuse.nikkeManager.domain.entities

import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.vo.DollLevel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DollTest {

    @Nested
    inner class CreationAndValidation {

        @Test
        fun `should create and validate doll state on reconstitution`() {
            val expectedDoll = Doll.reconstitute(
                id = 1,
                rarity = Rarity.R,
                level = DollLevel.of(1)
            )

            assertThat(expectedDoll).usingRecursiveComparison().isEqualTo(
                Doll.reconstitute(
                    id = 1,
                    rarity = Rarity.R,
                    level = DollLevel.of(1)
                )
            )
        }

        @Test
        fun `should throw exception when creating a doll with invalid level`() {
            val invalidLevel = 20
            val expectedMessage = "Doll max level must be 15"

            val exception = assertThrows<IllegalArgumentException> {
                Doll.create(
                    Rarity.SR,
                    DollLevel.of(invalidLevel)
                )
            }

            assertThat(exception.message).isEqualTo(expectedMessage)
        }
    }

    @Nested
    inner class BusinessLogic {

        @Test
        fun `should correct base data of an existing doll`() {

            val doll = Doll.create(
                rarity = Rarity.SR,
                level = DollLevel.of(1)
            )

            val updatedDoll = doll.correctBaseData(
                newRarity = Rarity.SSR,
                newLevel = DollLevel.of(15)
            )

            assertThat(updatedDoll.rarity).isEqualTo(Rarity.SSR)
            assertThat(updatedDoll.level.value).isEqualTo(15)
        }
    }
}