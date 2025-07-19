package com.hyuse.nikkeManager.domain.usecases.nikke

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.exceptions.CollectionEmptyException
import com.hyuse.nikkeManager.domain.ports.NikkeRepository

class GetAllNikkesCase(
    private val nikkeRepository: NikkeRepository
) {

    fun execute(): Collection<Nikke> {

        val nikkes = nikkeRepository.findAll()

        if (nikkes.isEmpty()) throw CollectionEmptyException()

        return nikkes
    }

}