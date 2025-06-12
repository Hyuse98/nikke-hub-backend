package com.hyuse.nikkeManager.domain.usecases.nikke

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeNotFoundException
import com.hyuse.nikkeManager.domain.ports.NikkeRepository

class GetNikkeByNameCase (
    private val nikkeRepository: NikkeRepository
){

    fun execute(name: String): Nikke {

        val existingNikke = nikkeRepository.existsByName(name)

        if(!existingNikke) throw NikkeNotFoundException(name)

        return nikkeRepository.findByName(name)

    }
}