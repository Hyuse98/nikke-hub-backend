package com.hyuse.nikkeManager.domain.entities

import com.hyuse.nikkeManager.domain.enums.Rarity

data class Doll(

    val id: Int? = null,
    val rarity: Rarity,
    val level: Int
) {

}