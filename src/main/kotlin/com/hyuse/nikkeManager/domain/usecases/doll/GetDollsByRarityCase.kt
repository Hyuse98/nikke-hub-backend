package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.ports.DollRepository

class GetDollsByRarityCase(
    private val dollRepository: DollRepository
) {

    fun execute(rarity: Rarity): Doll {

        //TODO Guard to check if exist

//        val doll = dollRepository.findByRarity(rarity)

        if(doll.isEmpty) throw Exception("Doll not Exist")

    }
}