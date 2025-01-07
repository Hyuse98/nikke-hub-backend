package com.hyuse.nikkeManager.dto

import com.hyuse.nikkeManager.enums.Rarity
import jakarta.validation.Validation
import jakarta.validation.Validator
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

}