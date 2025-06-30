package com.hyuse.nikkeManager.domain.usecases.nikke

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.enums.*
import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeAlreadyExistsException
import com.hyuse.nikkeManager.domain.ports.NikkeRepository

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

        val existingNikke = nikkeRepository.existsByName(name)

        if (existingNikke) throw NikkeAlreadyExistsException("Nikke with name $name already exists!")

        val newNikke = Nikke.create(
            name,
            core,
            attraction,
            skill1,
            skill2,
            skillBurst,
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