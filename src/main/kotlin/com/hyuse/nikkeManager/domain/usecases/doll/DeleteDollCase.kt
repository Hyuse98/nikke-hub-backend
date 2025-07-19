package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.exceptions.doll.DollNotFoundException
import com.hyuse.nikkeManager.domain.ports.DollRepository

class DeleteDollCase(
    private val dollRepository: DollRepository
) {
    fun execute(id: Int) {

        if (!dollRepository.existById(id)) {
            throw DollNotFoundException(id)
        }

        return dollRepository.delete(id)
    }
}