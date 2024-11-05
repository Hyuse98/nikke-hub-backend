package com.hyuse.nikkeManager.model

import com.hyuse.nikkeManager.enums.*
import jakarta.persistence.*

@Entity
class Nikke(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(length = 40, unique = true, nullable = false)
    val name: String,
    @Column
    val core: Int,
    @Column
    val attraction: Int,
    @Column
    val skill1Level: Int,
    @Column
    val skill2Level: Int,
    @Column
    val burstLevel: Int,

    @Enumerated(EnumType.STRING)
    val rarity: Rarity,
    @Enumerated(EnumType.STRING)
    val ownedStatus: OwnedStatus,
    @Enumerated(EnumType.STRING)
    val burstType: BurstType,
    @Enumerated(EnumType.STRING)
    val manufacturer: Manufacturer,
    @Enumerated(EnumType.STRING)
    val code: Code,
    @Enumerated(EnumType.STRING)
    val weapon: Weapon,
    @Enumerated(EnumType.STRING)
    val role: Role,
    @Enumerated(EnumType.STRING)
    val cube: Cubes,
    @ManyToOne
    @JoinColumn(name = "dollId")
    val doll: Doll?
) {

}