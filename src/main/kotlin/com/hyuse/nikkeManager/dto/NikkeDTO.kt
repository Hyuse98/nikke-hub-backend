package com.hyuse.nikkeManager.dto

import com.hyuse.nikkeManager.enums.*
import com.hyuse.nikkeManager.model.Doll
import com.hyuse.nikkeManager.model.Nikke
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class NikkeDTO(

    val id: Int?,
    @field:NotNull(message = "Rarity is required")
    val name: String,
    @field:Min(value = 1, message = "Core value must be at least 1")
    @field:Max(value = 10, message = "Cant be more than 10")
    val core: Int,
    @field:Min(value = 1, message = "Core value must be at least 1")
    @field:Max(value = 10, message = "Cant be more than 10")
    val attraction: Int,
    @field:Min(value = 1, message = "Core value must be at least 1")
    @field:Max(value = 10, message = "Cant be more than 10")
    val skill1Level: Int,
    @field:Min(value = 1, message = "Core value must be at least 1")
    @field:Max(value = 10, message = "Cant be more than 10")
    val skill2Level: Int,
    @field:Min(value = 1, message = "Core value must be at least 1")
    @field:Max(value = 10, message = "Cant be more than 10")
    val burstLevel: Int,
    @field:NotNull(message = "Rarity is required")
    val rarity: Rarity,
    @field:NotNull(message = "Rarity is required")
    val ownedStatus: OwnedStatus,
    @field:NotNull(message = "Rarity is required")
    val burstType: BurstType,
    @field:NotNull(message = "Rarity is required")
    val company: Company,
    @field:NotNull(message = "Rarity is required")
    val code: Code,
    @field:NotNull(message = "Rarity is required")
    val weapon: Weapon,
    @field:NotNull(message = "Rarity is required")
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