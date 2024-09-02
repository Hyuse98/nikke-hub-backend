package com.hyuse.nikkeManager.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.hyuse.nikkeManager.enums.*
import jakarta.annotation.Nullable
import jakarta.persistence.*

@Entity
class Nikke (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int? = null,
    //@Column(length = 40, unique = true, nullable = false)
    val name: String,
    //@Column()
//    val core: Int,
//    val attraction: Int,
//    val skill1Level: Int,
//    val skill2Level: Int,
//    val burstLevel: Int,
//    @Enumerated(EnumType.STRING)
//    val rarity: Rarity,
//    @Enumerated(EnumType.STRING)
//    val ownedStatus: OwnedStatus,
//    @Enumerated(EnumType.STRING)
//    val burstType: BurstType,
//    @Enumerated(EnumType.STRING)
//    val manufacturer: Manufacturer,
//    @Enumerated(EnumType.STRING)
//    val code: Code,
//    @Enumerated(EnumType.STRING)
//    val weapon: Weapon,
//    @Enumerated(EnumType.STRING)
//    val role: Role,
//    @Enumerated(EnumType.STRING)
//    val cube: Cubes,
    //Relation Attributes

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name="nikkeId")
    val gear: MutableList<Gear> = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = "dollId")
    val doll: Doll?

){

}