package com.hyuse.nikkeManager.dto

import com.hyuse.nikkeManager.enums.*
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class NikkeDTOTest {

    val validator: Validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    @DisplayName("Should validate if DTO is valid")
    fun validateNikkeDTO() {

        val nikkeDTO = NikkeDTO(
            id = null,
            name = "Test1",
            core = 1,
            attraction = 1,
            skill1Level = 1,
            skill2Level = 1,
            burstLevel = 1,
            rarity = Rarity.SSR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.PILGRIM,
            code = Code.ELECTRIC,
            weapon = Weapon.SR,
            nikkeClass = NikkeClass.ATTACKER,
            cube = null,
            doll = null
        )

        val violations = validator.validate(nikkeDTO)

        assertTrue(violations.isEmpty())
    }

    @Test
    @DisplayName("Should Test if converting correctly a DTO to a Model")
    fun toModelTest() {

        val nikkeDTO = NikkeDTO(
            id = null,
            name = "Test1",
            core = 1,
            attraction = 1,
            skill1Level = 1,
            skill2Level = 1,
            burstLevel = 1,
            rarity = Rarity.SSR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.PILGRIM,
            code = Code.ELECTRIC,
            weapon = Weapon.SR,
            nikkeClass = NikkeClass.ATTACKER,
            cube = null,
            doll = null
        )

        val nikkeModel = nikkeDTO.toModel()

        assertThat(nikkeModel.name).isEqualTo(nikkeDTO.name)
    }

}