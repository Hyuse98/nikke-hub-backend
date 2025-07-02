package com.hyuse.nikkeManager.domain.usecases.nikke

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.enums.*
import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeAlreadyExistsException
import com.hyuse.nikkeManager.domain.ports.NikkeRepository
import com.hyuse.nikkeManager.domain.vo.AttractionLevel
import com.hyuse.nikkeManager.domain.vo.CharacterName
import com.hyuse.nikkeManager.domain.vo.CoreLevel
import com.hyuse.nikkeManager.domain.vo.SkillLevel

class CreateNikkeCase(
    private val nikkeRepository: NikkeRepository
) {

    fun execute(
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

        val characterName = CharacterName.of(name)
        val coreLevel = CoreLevel.of(core)
        val attractionLevel = AttractionLevel.of(attraction)
        val skill1Level = SkillLevel.of(skill1)
        val skill2Level = SkillLevel.of(skill2)
        val skillBurstLevel = SkillLevel.of(skillBurst)

        val existingNikke = nikkeRepository.existsByName(name)

        if (existingNikke) throw NikkeAlreadyExistsException("Nikke with name $name already exists!")

        val newNikke = Nikke.create(
            characterName,
            coreLevel,
            attractionLevel,
            skill1Level,
            skill2Level,
            skillBurstLevel,
            rarity,
            ownedStatus,
            burstType,
            company,
            code,
            weapon,
            nikkeClass
        )

        return nikkeRepository.save(newNikke)
    }
}