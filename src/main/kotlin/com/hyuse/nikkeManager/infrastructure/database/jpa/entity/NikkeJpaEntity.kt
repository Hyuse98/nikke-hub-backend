package com.hyuse.nikkeManager.infrastructure.database.jpa.entity

import com.hyuse.nikkeManager.domain.enums.*
import jakarta.persistence.*

@Entity
class NikkeJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nikke_id")
    val id: Int? = null,

    @Column(name = "character_name", unique = true)
    val name: String,

    @Column(name = "core_level")
    val core: Int,

    @Column(name = "attraction")
    val attraction: Int,

    @Column(name = "skill_1_level")
    val skill1: Int,

    @Column(name = "skill_2_level")
    val skill2: Int,

    @Column(name = "skill_burst")
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
    val cube: Cubes? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NikkeJpaEntity

        if (id != other.id) return false
        if (name != other.name) return false
        if (rarity != other.rarity) return false
        if (burstType != other.burstType) return false
        if (company != other.company) return false
        if (code != other.code) return false
        if (weapon != other.weapon) return false
        if (nikkeClass != other.nikkeClass) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + rarity.hashCode()
        result = 31 * result + burstType.hashCode()
        result = 31 * result + company.hashCode()
        result = 31 * result + code.hashCode()
        result = 31 * result + weapon.hashCode()
        result = 31 * result + nikkeClass.hashCode()
        return result
    }

    override fun toString(): String {
        return "NikkeJpaEntity(id=$id, name=$name, core=$core, attraction=$attraction, skill1=$skill1, skill2=$skill2, skillBurst=$skillBurst, rarity=$rarity, ownedStatus=$ownedStatus, burstType=$burstType, company=$company, code=$code, weapon=$weapon, nikkeClass=$nikkeClass, cube=$cube)"
    }
}
