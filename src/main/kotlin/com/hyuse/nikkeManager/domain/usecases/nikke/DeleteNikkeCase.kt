package com.hyuse.nikkeManager.domain.usecases.nikke

import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeIdNotFoundException
import com.hyuse.nikkeManager.domain.ports.NikkeRepository

class DeleteNikkeCase(
    private val nikkeRepository: NikkeRepository
) {

    fun execute(id: Int) {

        val existingNikke = nikkeRepository.existsById(id)

        if (!existingNikke) throw NikkeIdNotFoundException("$id")

        nikkeRepository.delete(id)
    }

}