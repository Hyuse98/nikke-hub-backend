package com.hyuse.nikkeManager.dto

import com.hyuse.nikkeManager.enums.*
import com.hyuse.nikkeManager.model.Doll
import com.hyuse.nikkeManager.model.Nikke
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class NikkeDTO(

    val id: Int?,

    @field:NotNull(message = "Name cannot be empty")
    val name: String,

    @field:Min(value = 0, message = "Core value must be at least 0")
    @field:Max(value = 10, message = "Core cant be more than 10")
    @field:NotNull(message = "Name cannot be empty")
    val core: Int,

    @field:Min(value = 1, message = "attraction value must be at least 1")
    @field:Max(value = 30, message = "attraction cant be more than 30")
    @field:NotNull(message = "Name cannot be empty")
    val attraction: Int,

    @field:Min(value = 1, message = "skill1Level value must be at least 1")
    @field:Max(value = 10, message = "skill1Level cant be more than 10")
    @field:NotNull(message = "Name cannot be empty")
    val skill1Level: Int,

    @field:Min(value = 1, message = "skill2Level value must be at least 1")
    @field:Max(value = 10, message = "skill2Level cant be more than 10")
    @field:NotNull(message = "Name cannot be empty")
    val skill2Level: Int,

    @field:Min(value = 1, message = "burstLevel value must be at least 1")
    @field:Max(value = 10, message = " burstLevel cant be more than 10")
    @field:NotNull(message = "Name cannot be empty")
    val burstLevel: Int,

    @field:NotNull(message = "Rarity is required")
    val rarity: Rarity,

    @field:NotNull(message = "OwnedStatus is required")
    val ownedStatus: OwnedStatus,

    @field:NotNull(message = "BurstType is required")
    val burstType: BurstType,

    @field:NotNull(message = "Company is required")
    val company: Company,

    @field:NotNull(message = "Code is required")
    val code: Code,

    @field:NotNull(message = "Weapon is required")
    val weapon: Weapon,

    @field:NotNull(message = "NikkeClass is required")
    val nikkeClass: NikkeClass,

    val cube: Cubes?,

    val doll: Doll?
) {
    fun toModel(): Nikke {
        val nikke = Nikke(
            id = this.id,
            name = this.name,
            core = this.core,
            attraction = this.attraction,
            skill1Level = this.skill1Level,
            skill2Level = this.skill2Level,
            burstLevel = this.burstLevel,
            rarity = this.rarity,
            ownedStatus = this.ownedStatus,
            burstType = this.burstType,
            company = this.company,
            code = this.code,
            weapon = this.weapon,
            nikkeClass = this.nikkeClass,
            cube = this.cube,
            doll = this.doll,
        )
        return nikke
    }
}