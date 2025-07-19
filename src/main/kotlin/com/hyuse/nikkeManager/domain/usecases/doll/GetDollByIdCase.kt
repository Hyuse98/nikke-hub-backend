package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.exceptions.doll.DollNotFoundException
import com.hyuse.nikkeManager.domain.ports.DollRepository

class GetDollByIdCase(
    private val dollRepository: DollRepository
) {

    fun execute(id: Int): Doll {

        val existingDoll = dollRepository.existById(id)

        if(!existingDoll) throw DollNotFoundException(id)

        return dollRepository.findById(id).get()
    }
}