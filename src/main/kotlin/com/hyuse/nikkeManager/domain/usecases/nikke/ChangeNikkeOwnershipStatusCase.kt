package com.hyuse.nikkeManager.domain.usecases.nikke

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.enums.OwnedStatus
import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeIdNotFoundException
import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeNotFoundException
import com.hyuse.nikkeManager.domain.ports.NikkeRepository

class ChangeNikkeOwnershipStatusCase (
    private val nikkeRepository: NikkeRepository
){

    fun execute(id: Int): Nikke {

        val existingNikke = nikkeRepository.findById(id)

        if (existingNikke.isEmpty) throw NikkeIdNotFoundException(id.toString())

        val updatedNikke = existingNikke.get().updateOwnedStatus(OwnedStatus.OWNED)

        return nikkeRepository.update(updatedNikke)
    }
}