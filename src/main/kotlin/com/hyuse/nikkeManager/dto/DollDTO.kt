package com.hyuse.nikkeManager.dto

import com.hyuse.nikkeManager.enums.Rarity
import com.hyuse.nikkeManager.model.Doll
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class DollDTO(

    val id: Int?,

    @field:NotNull(message = "Rarity is required")
    val rarity: Rarity,

    @field:Min(0, message = "level value must be at least 0")
    @field:Max(15, message = "Cant be more than 15")
    val level: Int
) {
    fun toModel(): Doll {
        val doll = Doll(
            id = this.id,
            rarity = this.rarity,
            level = this.level
        )
        return doll
    }
}