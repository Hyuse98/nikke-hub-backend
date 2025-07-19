package com.hyuse.nikkeManager.domain.entities

import com.hyuse.nikkeManager.domain.enums.*
import com.hyuse.nikkeManager.domain.vo.AttractionLevel
import com.hyuse.nikkeManager.domain.vo.CharacterName
import com.hyuse.nikkeManager.domain.vo.CoreLevel
import com.hyuse.nikkeManager.domain.vo.SkillLevel

class Nikke private constructor(

    val id: Int? = null,
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
    cube: Cubes? = null,
//    doll: Doll? = null
) {

    var name: CharacterName = name
        private set
    var core: CoreLevel = core
        private set
    var attraction: AttractionLevel = attraction
        private set
    var skill1: SkillLevel = skill1
        private set
    var skill2: SkillLevel = skill2
        private set
    var skillBurst: SkillLevel = skillBurst
        private set
    var rarity: Rarity = rarity
        private set
    var ownedStatus: OwnedStatus = ownedStatus
        private set
    var burstType: BurstType = burstType
        private set
    var company: Company = company
        private set
    var code: Code = code
        private set
    var weapon: Weapon = weapon
        private set
    var nikkeClass: NikkeClass = nikkeClass
        private set
    var cube: Cubes? = cube
        private set

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