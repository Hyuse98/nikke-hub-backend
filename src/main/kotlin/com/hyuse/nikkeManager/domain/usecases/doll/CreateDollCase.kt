package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.ports.DollRepository
import com.hyuse.nikkeManager.domain.vo.DollLevel

class CreateDollCase(
    private val dollRepository: DollRepository
) {

    fun execute(rarity: Rarity, level: Int): Doll {

        val dollLevel = DollLevel.of(level)

        val newDoll = Doll.create(rarity, dollLevel)

        // TODO: dollRepository.findByProperties(rarity, level)?.let { throw Exception(...) }

        return dollRepository.save(newDoll)
    }
}