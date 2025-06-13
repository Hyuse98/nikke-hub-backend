package com.hyuse.nikkeManager.domain.entities

import com.hyuse.nikkeManager.domain.enums.*

class Nikke private constructor(

    val id: Int? = null,
    var name: String,
    var core: Int,
    var attraction: Int,
    var skill1: Int,
    var skill2: Int,
    var skillBurst: Int,
    var rarity: Rarity,
    var ownedStatus: OwnedStatus,
    var burstType: BurstType,
    var company: Company,
    var code: Code,
    var weapon: Weapon,
    var nikkeClass: NikkeClass,
    var cube: Cubes? = null,
//    var doll: Doll? = null
) {

    init {
        validate()
    }

    fun correctBaseData(
        name: String,
        rarity: Rarity,
        burstType: BurstType,
        company: Company,
        code: Code,
        weapon: Weapon,
        nikkeClass: NikkeClass
    ): Nikke {
        this.name = name
        this.rarity = rarity
        this.burstType = burstType
        this.company = company
        this.code = code
        this.weapon = weapon
        this.nikkeClass = nikkeClass
        validate()
        return this
    }

    fun skill1LevelUp(): Nikke {
        this.skill1 += 1
        validate()
        return this
    }

    fun skill2LevelUp(): Nikke {
        this.skill2 += 1
        validate()
        return this
    }

    fun skillBurstLevelUp(): Nikke {
        this.skillBurst += 1
        validate()
        return this
    }

    fun attractionIncrement(): Nikke {
        this.attraction += 1
        validate()
        return this
    }

    fun coreLevelUp(): Nikke {
        this.core += 1
        validate()
        return this
    }

    fun updateOwnedStatus(ownedStatus: OwnedStatus): Nikke {
        this.ownedStatus = ownedStatus
        validate()
        return this
    }

    fun updateCube(cubes: Cubes): Nikke {
        this.cube = cubes
        validate()
        return this
    }

    private fun validate() {

        if (core < CORE_MIN_LEVEL || core > CORE_MAX_LEVEL) {
            throw IllegalArgumentException("Core must be Higher than $CORE_MIN_LEVEL and Lower than $CORE_MAX_LEVEL")
        }

        if (attraction < ATTRACTION_MIN_LEVEL || attraction > ATTRACTION_MAX_LEVEL) {
            throw IllegalArgumentException("Attraction must be Higher than $ATTRACTION_MIN_LEVEL and Lower than $ATTRACTION_MAX_LEVEL")
        }

        if (skill1 < SKILL_MIN_LEVEL || skill1 > SKILL_MAX_LEVEL) {
            throw IllegalArgumentException("Skill 1 must be Higher than $SKILL_MIN_LEVEL and Lower than $SKILL_MAX_LEVEL")
        }

        if (skill2 < SKILL_MIN_LEVEL || skill2 > SKILL_MAX_LEVEL) {
            throw IllegalArgumentException("Skill 2 must be Higher than $SKILL_MIN_LEVEL and Lower than $SKILL_MAX_LEVEL")
        }

        if (skillBurst < SKILL_MIN_LEVEL || skillBurst > SKILL_MAX_LEVEL) {
            throw IllegalArgumentException("Skill Burst must be Higher than $SKILL_MIN_LEVEL and Lower than $SKILL_MAX_LEVEL")
        }
    }

    companion object {

        private const val CORE_MIN_LEVEL = 0
        private const val CORE_MAX_LEVEL = 10
        private const val SKILL_MIN_LEVEL = 1
        private const val SKILL_MAX_LEVEL = 10
        private const val ATTRACTION_MIN_LEVEL = 1
        private const val ATTRACTION_MAX_LEVEL = 30


        fun create(
            name: String,
            core: Int,
            attraction: Int,
            skill1: Int,
            skill2: Int,
            skillBurst: Int,
            rarity: Rarity,
            ownedStatus: OwnedStatus,
            burstType: BurstType,
            company: Company,
            code: Code,
            weapon: Weapon,
            nikkeClass: NikkeClass
        ): Nikke {
            return Nikke(
                name = name,
                core = core,
                attraction = attraction,
                skill1 = skill1,
                skill2 = skill2,
                skillBurst = skillBurst,
                rarity = rarity,
                ownedStatus = ownedStatus,
                burstType = burstType,
                company = company,
                code = code,
                weapon = weapon,
                nikkeClass = nikkeClass
            )
        }

        fun createWhitId(
            name: String,
            core: Int,
            attraction: Int,
            skill1: Int,
            skill2: Int,
            skillBurst: Int,
            rarity: Rarity,
            ownedStatus: OwnedStatus,
            burstType: BurstType,
            company: Company,
            code: Code,
            weapon: Weapon,
            nikkeClass: NikkeClass
        ): Nikke {
            return Nikke(
                name = name,
                core = core,
                attraction = attraction,
                skill1 = skill1,
                skill2 = skill2,
                skillBurst = skillBurst,
                rarity = rarity,
                ownedStatus = ownedStatus,
                burstType = burstType,
                company = company,
                code = code,
                weapon = weapon,
                nikkeClass = nikkeClass
            )
        }
    }
}