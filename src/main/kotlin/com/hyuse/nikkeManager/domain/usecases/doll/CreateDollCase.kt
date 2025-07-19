package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.exceptions.doll.DollAlreadyExistsException
import com.hyuse.nikkeManager.domain.ports.DollRepository
import com.hyuse.nikkeManager.domain.vo.DollLevel

class CreateDollCase(
    private val dollRepository: DollRepository
) {

    fun execute(rarity: Rarity, level: Int): Doll {

        val dollLevel = DollLevel.of(level)

        val newDoll = Doll.create(rarity, dollLevel)

        val existingDoll = dollRepository.findByRarityAndLevel(rarity, dollLevel)

        if(existingDoll.isPresent){
            throw DollAlreadyExistsException(rarity, level)
        }

        return dollRepository.save(newDoll)
    }
}