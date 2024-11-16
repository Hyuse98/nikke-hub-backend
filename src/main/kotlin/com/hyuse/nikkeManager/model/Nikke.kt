package com.hyuse.nikkeManager.model

import com.hyuse.nikkeManager.enums.*
import jakarta.persistence.*

@Entity
class Nikke(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int? = null,

    val name: String,

    val core: Int,

    val attraction: Int,

    val skill1Level: Int,

    val skill2Level: Int,

    val burstLevel: Int,

    @Enumerated(EnumType.STRING)
    val rarity: Rarity,
    @Enumerated(EnumType.STRING)
    val ownedStatus: OwnedStatus,
    @Enumerated(EnumType.STRING)
    val burstType: BurstType,
    @Enumerated(EnumType.STRING)
    val company: Company,
    @Enumerated(EnumType.STRING)
    val code: Code,
    @Enumerated(EnumType.STRING)
    val weapon: Weapon,
    @Enumerated(EnumType.STRING)
    val nikkeClass: NikkeClass,
    @Enumerated(EnumType.STRING)
    val cube: Cubes?,
    @ManyToOne
    @JoinColumn(name = "dollId")
    val doll: Doll?
) {

}