package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.ports.DollRepository

class CreateDollCase(
    private val dollRepository: DollRepository
) {

    fun execute(doll: Doll): Doll {

        //TODO Guard to check if doll exist looking to Rarity and Level

        return dollRepository.save(doll)
    }
}