package com.hyuse.nikkeManager.domain.usecases.nikke

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeNotFoundException
import com.hyuse.nikkeManager.domain.ports.NikkeRepository

class LevelUpNikkeSkill1Case(
    private val nikkeRepository: NikkeRepository
) {

    fun execute(id: Int): Nikke {

        val existingNikke = nikkeRepository.findById(id)

        if (existingNikke.isEmpty) throw NikkeNotFoundException(id)

        val updatedNikke = existingNikke.get().skill1LevelUp()

        return nikkeRepository.update(updatedNikke)
    }
}