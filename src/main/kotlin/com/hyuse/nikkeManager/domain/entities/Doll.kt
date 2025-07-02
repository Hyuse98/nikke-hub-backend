package com.hyuse.nikkeManager.domain.entities

import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.vo.DollLevel

class Doll private constructor(
    val id: Int? = null,
    var rarity: Rarity,
    var level: DollLevel
) {

    init {
        validate()
    }

    fun correctBaseData(
        newRarity: Rarity,
        newLevel: DollLevel
    ) {
        this.rarity = newRarity
        this.level = newLevel
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