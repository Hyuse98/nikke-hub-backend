package com.hyuse.nikkeManager.model

import com.hyuse.nikkeManager.enums.*
import jakarta.persistence.*

@Entity
class Nikke(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int? = null,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val core: Int,

    @Column(nullable = false)
    val attraction: Int,

    @Column(nullable = false)
    val skill1Level: Int,

    @Column(nullable = false)
    val skill2Level: Int,

    @Column(nullable = false)
    val burstLevel: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val rarity: Rarity,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val ownedStatus: OwnedStatus,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val burstType: BurstType,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val company: Company,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val code: Code,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val weapon: Weapon,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val nikkeClass: NikkeClass,

    @Enumerated(EnumType.STRING)
    val cube: Cubes? = null,

    @ManyToOne
    @JoinColumn(name = "dollId")
    val doll: Doll? = null
) {
}