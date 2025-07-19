package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.exceptions.CollectionEmptyException
import com.hyuse.nikkeManager.domain.ports.DollRepository

class GetAllDollsCase(
    private val dollRepository: DollRepository
) {

    fun execute(): Collection<Doll> {

        val dolls = dollRepository.findAll()

        if(dolls.isEmpty()) throw CollectionEmptyException()

        return dolls
    }
}