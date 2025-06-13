package com.hyuse.nikkeManager.domain.entities

import com.hyuse.nikkeManager.domain.enums.Rarity

class Doll private constructor(
    val id: Int? = null,
    var rarity: Rarity,
    var level: Int
) {

    init {
        validate()
    }

    fun updateInfo(newRarity: Rarity, newLevel: Int) {
        this.rarity = newRarity
        this.level = newLevel
        validate()
    }

    private fun validate() {
        if (level < MIN_LEVEL || level > MAX_LEVEL) {
            throw IllegalArgumentException("Doll level must be between $MIN_LEVEL and $MAX_LEVEL.")
        }

    }

    companion object {
        private const val MIN_LEVEL = 0
        private const val MAX_LEVEL = 15

        fun create(rarity: Rarity, level: Int): Doll {
            return Doll(
                rarity = rarity,
                level = level
            )
        }

        fun withId(id: Int, rarity: Rarity, level: Int): Doll {
            return Doll(
                id = id,
                rarity = rarity,
                level = level
            )
        }
    }
}