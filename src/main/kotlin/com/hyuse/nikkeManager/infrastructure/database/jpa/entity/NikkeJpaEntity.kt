package com.hyuse.nikkeManager.infrastructure.database.jpa.entity

import com.hyuse.nikkeManager.domain.enums.*
import com.hyuse.nikkeManager.domain.vo.AttractionLevel
import com.hyuse.nikkeManager.domain.vo.CharacterName
import com.hyuse.nikkeManager.domain.vo.CoreLevel
import com.hyuse.nikkeManager.domain.vo.SkillLevel
import jakarta.persistence.*

@Entity
class NikkeJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nikke_id")
    val id: Int? = null,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "character_name"))
    val name: CharacterName,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "core_level"))
    val core: CoreLevel,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "attraction"))
    val attraction: AttractionLevel,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "skill_1_level"))
    val skill1: SkillLevel,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "skill_2_level"))
    val skill2: SkillLevel,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "skill_burst"))
    val skillBurst: SkillLevel,

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
