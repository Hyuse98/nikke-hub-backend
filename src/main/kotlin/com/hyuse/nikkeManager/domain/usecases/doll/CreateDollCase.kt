package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.ports.DollRepository

class CreateDollCase(
    private val dollRepository: DollRepository
) {

    data class Input(
        val id: Int?,
        val rarity: Rarity,
        val level: Int
    )

    fun execute(input: Input): Doll {

        //TODO Guard to check if doll exist looking to Rarity and Level

        val doll = Doll(
            id = input.id,
            rarity = input.rarity,
            level = input.level
        )

        return dollRepository.save(doll)
    }
}