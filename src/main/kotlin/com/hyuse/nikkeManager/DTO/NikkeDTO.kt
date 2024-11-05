package com.hyuse.nikkeManager.DTO

import com.hyuse.nikkeManager.enums.*
import com.hyuse.nikkeManager.model.*

data class NikkeDTO(
    val id: Int?,
    val name: String,
    val core: Int,
    val attraction: Int,
    val skill1Level: Int,
    val skill2Level: Int,
    val burstLevel: Int,
    val rarity: Rarity,
    val ownedStatus: OwnedStatus,
    val burstType: BurstType,
    val manufacturer: Manufacturer,
    val code: Code,
    val weapon: Weapon,
    val role: Role,
    val cube: Cubes,
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
            manufacturer = this.manufacturer,
            code = this.code,
            weapon = this.weapon,
            role = this.role,
            cube = this.cube,
            doll = this.doll,

        )
        return nikke
    }

}