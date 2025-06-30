package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.ports.DollRepository

class DeleteDollCase (
    private val dollRepository: DollRepository
){
    fun execute(id: Int): Boolean {

        if (!dollRepository.existById(id)) {
            throw Exception("Doll with ID $id not found, cannot delete.")
        }

        return dollRepository.delete(id)
    }
}