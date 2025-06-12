package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.ports.DollRepository

class GetDollByIdCase(
    private val dollRepository: DollRepository
) {

    fun execute(id: Int): Doll {

        val existingDoll = dollRepository.existById(id)

        if(!existingDoll) throw Exception("Doll not Exist")

        return dollRepository.findById(id)
    }
}