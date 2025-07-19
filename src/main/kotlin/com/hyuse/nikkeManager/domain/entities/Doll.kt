package com.hyuse.nikkeManager.domain.entities

import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.vo.DollLevel

class Doll private constructor(
    val id: Int? = null,
    rarity: Rarity,
    level: DollLevel
) {

    var rarity = rarity
        private set
    var level = level
        private set

    init {
        validate()
    }

    fun correctBaseData(
        newRarity: Rarity,
        newLevel: DollLevel
    ): Doll {
        this.rarity = newRarity
        this.level = newLevel
        return this
    }

    private fun validate() {
    }

    companion object {

        fun create(rarity: Rarity, level: DollLevel): Doll {
            return Doll(
                rarity = rarity,
                level = level
            )
        }

        fun reconstitute(id: Int?, rarity: Rarity, level: DollLevel): Doll {
            return Doll(
                id = id,
                rarity = rarity,
                level = level
            )
        }
    }
}