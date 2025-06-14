package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.ports.DollRepository

class CreateDollCase(
    private val dollRepository: DollRepository
) {

    fun execute(rarity: Rarity, level: Int): Doll {

        val newDoll = Doll.create(rarity, level)

        // TODO: dollRepository.findByProperties(rarity, level)?.let { throw Exception(...) }

        return dollRepository.save(newDoll)
    }
}