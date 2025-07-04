package com.hyuse.nikkeManager.domain.vo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CharacterNameTest {

    @Nested
    inner class CreationAndValidation {

        @Test
        fun `should create a character name when name is valid`() {

            val validName = "Mast"

            val characterName = CharacterName.of(validName)

            assertThat(characterName.value).isEqualTo(validName)
        }

        @Test
        fun `should not create a character name when name length is above maximum size`() {

            val invalidNameSize = "MastMastMastMastMastMastMastMastMastMastMastMastMast"

            val exception = assertThrows<IllegalArgumentException> {
                CharacterName.of(invalidNameSize)
            }

            assertThat(exception.message).isEqualTo("Nikke name is too long, cant be greater than 50 characters")
        }

        @Test
        fun `should not create a character name when name is blank`() {

            val invalidName = ""

            val exception = assertThrows<IllegalArgumentException> {
                CharacterName.of(invalidName)
            }

            assertThat(exception.message).isEqualTo("Nikke name cant be blank")
        }
    }
}