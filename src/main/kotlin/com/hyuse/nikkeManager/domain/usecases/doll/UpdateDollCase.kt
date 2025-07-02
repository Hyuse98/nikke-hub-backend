package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.exceptions.doll.DollNotFoundException
import com.hyuse.nikkeManager.domain.ports.DollRepository
import com.hyuse.nikkeManager.domain.vo.DollLevel

class UpdateDollCase(
    private val dollRepository: DollRepository
) {

    fun execute(id: Int, rarity: Rarity, level: Int): Doll {

        val dollLevel = DollLevel.of(level)

        val existingDoll = dollRepository.findById(id)

        if(existingDoll.isEmpty) throw DollNotFoundException(rarity, level)

        existingDoll.get().correctBaseData(rarity, dollLevel)

        return dollRepository.update(id, existingDoll.get())
    }
}