package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.ports.DollRepository

class UpdateDollCase(
    private val dollRepository: DollRepository
) {

    fun execute(id: Int, doll: Doll): Doll {

        val existingDoll = dollRepository.existById(id)

        if (existingDoll) throw Exception("Doll Already Exist")

        val updatedDoll = Doll(
            id = id,
            rarity = doll.rarity,
            level = doll.level
        )

        return dollRepository.update(id, updatedDoll)
    }
}