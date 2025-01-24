package com.hyuse.nikkeManager.dto

import com.hyuse.nikkeManager.enums.*
import com.hyuse.nikkeManager.model.Doll
import com.hyuse.nikkeManager.model.Nikke
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class NikkeDTOTest {

    private val validator: Validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    @DisplayName("Should validate if DTO is valid")
    fun validateValidNikkeDTO() {

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
    @DisplayName("Should fail validation when fields have invalid values")
    fun validateInvalidNikkeDTO() {

        val nikkeDTO = NikkeDTO(
            id = null,
            name = "Test",
            core = 1,
            attraction = 1,
            skill1Level = 1,
            skill2Level = 1,
            burstLevel = 19,
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

        assertFalse(violations.isEmpty())
        assertThat(violations.any { it.message == "Core cant be more than 10" })
        assertThat(violations.any { it.message == "attraction cant be more than 10" })
        assertThat(violations.any { it.message == "skill1Level cant be more than 10" })
        assertThat(violations.any { it.message == "skill2Level cant be more than 10" })
        assertThat(violations.any { it.message == "burstLevel cant be more than 10" })
    }

    @Test
    @DisplayName("Should Test if converting correctly a DTO to a Model")
    fun toModelTest() {

        val nikkeDTO = NikkeDTO(
            id = 1,
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
            cube = Cubes.ASSAULT,
            doll = Doll(
                id = 1,
                rarity = Rarity.R,
                level = 0
            )
        )

        val nikkeModel: Nikke = nikkeDTO.toModel()

        assertThat(nikkeModel.id).isEqualTo(nikkeDTO.id)
        assertThat(nikkeModel.name).isEqualTo(nikkeDTO.name)
        assertThat(nikkeModel.core).isEqualTo(nikkeDTO.core)
        assertThat(nikkeModel.attraction).isEqualTo(nikkeDTO.attraction)
        assertThat(nikkeModel.skill1Level).isEqualTo(nikkeDTO.skill1Level)
        assertThat(nikkeModel.skill2Level).isEqualTo(nikkeDTO.skill2Level)
        assertThat(nikkeModel.burstLevel).isEqualTo(nikkeDTO.burstLevel)
        assertThat(nikkeModel.rarity).isEqualTo(nikkeDTO.rarity)
        assertThat(nikkeModel.ownedStatus).isEqualTo(nikkeDTO.ownedStatus)
        assertThat(nikkeModel.burstType).isEqualTo(nikkeDTO.burstType)
        assertThat(nikkeModel.company).isEqualTo(nikkeDTO.company)
        assertThat(nikkeModel.code).isEqualTo(nikkeDTO.code)
        assertThat(nikkeModel.weapon).isEqualTo(nikkeDTO.weapon)
        assertThat(nikkeModel.nikkeClass).isEqualTo(nikkeDTO.nikkeClass)
        assertThat(nikkeModel.cube).isEqualTo(nikkeDTO.cube)
        assertThat(nikkeModel.doll).isEqualTo(nikkeDTO.doll)
    }

}