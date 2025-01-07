package com.hyuse.nikkeManager.dto

import com.hyuse.nikkeManager.enums.*
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class DollDTOTest {

    private val validator: Validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    @DisplayName("Should validate if DTO is valid")
    fun validateValidDollDTO() {

        val dollDTO = DollDTO(
            id = 1,
            rarity = Rarity.SSR,
            level = 15
        )

        val violations = validator.validate(dollDTO)

        assertTrue(violations.isEmpty())
    }

    @Test
    @DisplayName("Should fail validation when level is above 15")
    fun invalidDollDTOCase1() {

        val dollDTO = DollDTO(
            id = 1,
            rarity = Rarity.SSR,
            level = 16
        )

        val violations = validator.validate(dollDTO)

        assertFalse(violations.isEmpty())
        assertTrue(violations.any { it.message == "Cant be more than 15" })
    }

    @Test
    @DisplayName("Should fail validation when level is bellow 0")
    fun invalidDollDTOCase2() {

        val dollDTO = DollDTO(
            id = 1,
            rarity = Rarity.SSR,
            level = -1
        )

        val violations = validator.validate(dollDTO)

        assertFalse(violations.isEmpty())
        assertTrue(violations.any { it.message == "level value must be at least 0" })
    }

}