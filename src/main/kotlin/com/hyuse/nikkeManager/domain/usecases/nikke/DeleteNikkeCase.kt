package com.hyuse.nikkeManager.domain.usecases.nikke

import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeNotFoundException
import com.hyuse.nikkeManager.domain.ports.NikkeRepository

class DeleteNikkeCase(
    private val nikkeRepository: NikkeRepository
) {

    fun execute(id: Int) {

        val existingNikke = nikkeRepository.existsById(id)

        if (!existingNikke) throw NikkeNotFoundException(id)

        nikkeRepository.delete(id)
    }

}