package com.hyuse.nikkeManager.dto

import com.hyuse.nikkeManager.enums.Rarity
import com.hyuse.nikkeManager.model.Doll

data class DollDTO(
    val rarity: Rarity,
    val level: Int
) {
    fun toModel(): Doll{
        val doll = Doll(
            rarity = this.rarity,
            level = this.level
        )
        return doll
    }
}