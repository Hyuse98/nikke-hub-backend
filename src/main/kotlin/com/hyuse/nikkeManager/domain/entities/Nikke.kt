package com.hyuse.nikkeManager.domain.entities

import com.hyuse.nikkeManager.domain.enums.*

data class Nikke(

    val id: Int? = null,
    val name: String,
    val core: Int,
    val attraction: Int,
    val skill1: Int,
    val skill2: Int,
    val skillBurst: Int,
    val rarity: Rarity,
    val ownedStatus: OwnedStatus,
    val burstType: BurstType,
    val company: Company,
    val code: Code,
    val weapon: Weapon,
    val nikkeClass: NikkeClass,
    val cube: Cubes? = null,
//    val doll: Doll? = null

){

}
