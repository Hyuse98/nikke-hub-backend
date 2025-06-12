package com.hyuse.nikkeManager.infrastructure.database.jpa.entity

import com.hyuse.nikkeManager.domain.enums.*
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
data class NikkeJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nikke_id")
    val id: Int? = null,

    @Column(name = "name")
    val name: String,

    @Column(name = "core_level")
    val core: Int,

    @Column(name = "attraction")
    val attraction: Int,

    @Column(name = "skill_1_level")
    val skill1: Int,

    @Column(name = "skill_2_level")
    val skill2: Int,

    @Column(name = "skill_burst_level")
    val skillBurst: Int,

    @Column(name = "nikke_rarity")
    val rarity: Rarity,

    @Column(name = "owned")
    val ownedStatus: OwnedStatus,

    @Column(name = "burst_type")
    val burstType: BurstType,

    @Column(name = "company")
    val company: Company,

    @Column(name = "code")
    val code: Code,

    @Column(name = "weapon")
    val weapon: Weapon,

    @Column(name = "class")
    val nikkeClass: NikkeClass,

    @Column(name = "cube")
    val cube: Cubes? = null,

//    @Column(name = "doll_id")
//    @OneToMany
//    val doll: Doll? = null
)
