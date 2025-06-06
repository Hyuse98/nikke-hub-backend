package com.hyuse.nikkeManager.infrastructure.web.dto

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.enums.*
import com.hyuse.nikkeManager.domain.entities.Nikke
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class NikkeDTO(

    val id: Int?,

    @field:NotNull(message = "Name cannot be empty")
    val name: String,

    @field:Min(value = 0, message = "Core value must be at least 0")
    @field:Max(value = 10, message = "Core cant be more than 10")
    @field:NotNull(message = "Core cannot be empty")
    val core: Int,

    @field:Min(value = 1, message = "Attraction value must be at least 1")
    @field:Max(value = 30, message = "Attraction cant be more than 30")
    @field:NotNull(message = "Attraction cannot be empty")
    val attraction: Int,

    @field:Min(value = 1, message = "Skill 1 value must be at least 1")
    @field:Max(value = 10, message = "Skill 1 cant be more than 10")
    @field:NotNull(message = "Skill 1 cannot be empty")
    val skill1: Int,

    @field:Min(value = 1, message = "Skill 2 value must be at least 1")
    @field:Max(value = 10, message = "Skill 2 cant be more than 10")
    @field:NotNull(message = "Skill 2 cannot be empty")
    val skill2: Int,

    @field:Min(value = 1, message = "Skill Burst value must be at least 1")
    @field:Max(value = 10, message = " Skill Burst cant be more than 10")
    @field:NotNull(message = "Skill Burst cannot be empty")
    val skillBurst: Int,

    @field:NotNull(message = "Rarity is required")
    val rarity: Rarity,

    @field:NotNull(message = "Owned Status is required")
    val ownedStatus: OwnedStatus,

    @field:NotNull(message = "Burst Type is required")
    val burstType: BurstType,

    @field:NotNull(message = "Company is required")
    val company: Company,

    @field:NotNull(message = "Code is required")
    val code: Code,

    @field:NotNull(message = "Weapon is required")
    val weapon: Weapon,

    @field:NotNull(message = "Nikke Class is required")
    val nikkeClass: NikkeClass,

    val cube: Cubes?,

//    val doll: Doll?
)