package com.hyuse.nikkeManager.dto

import com.hyuse.nikkeManager.enums.Rarity
import com.hyuse.nikkeManager.model.Doll
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class DollDTO(

    val id: Int?,

    @NotNull(message = "Rarity is required")
    val rarity: Rarity,

    @Min(value = 0, message = "Core value must be at least 1")
    @Max(value = 15, message = "Cant be more than 15")
    val level: Int
) {
    fun toModel(): Doll {
        val doll = Doll(
            rarity = this.rarity,
            level = this.level
        )
        return doll
    }
}