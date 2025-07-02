package com.hyuse.nikkeManager.domain.usecases.nikke

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.enums.*
import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeNotFoundException
import com.hyuse.nikkeManager.domain.ports.NikkeRepository
import com.hyuse.nikkeManager.domain.vo.CharacterName

class CorrectNikkeBaseDataCase(
    private val nikkeRepository: NikkeRepository
) {

    fun execute(
        name: String,
        rarity: Rarity,
        burstType: BurstType,
        company: Company,
        code: Code,
        weapon: Weapon,
        nikkeClass: NikkeClass
    ): Nikke {

        val characterName = CharacterName.of(name)

        val existingNikke = nikkeRepository.findByName(name)

        if (existingNikke.isEmpty) throw NikkeNotFoundException("Nikke with name $name not exists!")

        val updatedNikke = existingNikke.get().correctBaseData(
            characterName,
            rarity,
            burstType,
            company,
            code,
            weapon,
            nikkeClass,
        )

        return nikkeRepository.update(updatedNikke)
    }

}