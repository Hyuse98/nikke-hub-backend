package com.hyuse.nikkeManager.domain.entities

import com.hyuse.nikkeManager.domain.enums.*
import com.hyuse.nikkeManager.domain.vo.AttractionLevel
import com.hyuse.nikkeManager.domain.vo.CharacterName
import com.hyuse.nikkeManager.domain.vo.CoreLevel
import com.hyuse.nikkeManager.domain.vo.SkillLevel

class Nikke private constructor(

    val id: Int? = null,
    var name: CharacterName,
    var core: CoreLevel,
    var attraction: AttractionLevel,
    var skill1: SkillLevel,
    var skill2: SkillLevel,
    var skillBurst: SkillLevel,
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
        name: CharacterName,
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
        return this
    }

    fun skill1LevelUp(): Nikke {
        this.skill1 = skill1.levelUp()
        return this
    }

    fun skill2LevelUp(): Nikke {
        this.skill2 = skill2.levelUp()
        return this
    }

    fun skillBurstLevelUp(): Nikke {
        this.skillBurst = skillBurst.levelUp()
        return this
    }

    fun coreLevelUp(): Nikke {
        this.core = core.levelUp()
        return this
    }

    fun attractionIncrement(): Nikke {
        this.attraction = attraction.increment()
        return this
    }

    fun updateOwnedStatus(ownedStatus: OwnedStatus): Nikke {
        this.ownedStatus = ownedStatus
        return this
    }

    fun updateCube(cubes: Cubes): Nikke {
        this.cube = cubes
        return this
    }

    private fun validate() {
    }

    companion object {

        fun create(
            name: CharacterName,
            core: CoreLevel,
            attraction: AttractionLevel,
            skill1: SkillLevel,
            skill2: SkillLevel,
            skillBurst: SkillLevel,
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

        fun reconstitute(
            id: Int?,
            name: CharacterName,
            core: CoreLevel,
            attraction: AttractionLevel,
            skill1: SkillLevel,
            skill2: SkillLevel,
            skillBurst: SkillLevel,
            rarity: Rarity,
            ownedStatus: OwnedStatus,
            burstType: BurstType,
            company: Company,
            code: Code,
            weapon: Weapon,
            nikkeClass: NikkeClass,
            cube: Cubes?
        ): Nikke {
            return Nikke(
                id = id,
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
                nikkeClass = nikkeClass,
                cube = cube
            )
        }
    }
}