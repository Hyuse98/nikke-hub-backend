package com.hyuse.nikkeManager.domain.usecases.nikke

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.enums.Cubes
import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeIdNotFoundException
import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeNotFoundException
import com.hyuse.nikkeManager.domain.ports.NikkeRepository

class EquipNikkeCubeCase(
    private val nikkeRepository: NikkeRepository
) {

    fun execute(id: Int, cube: Cubes): Nikke {

        val existingNikke = nikkeRepository.findById(id)

        if (existingNikke.isEmpty) throw NikkeIdNotFoundException(id.toString())

        val updatedNikke = existingNikke.get().updateCube(cube)

        return nikkeRepository.update(updatedNikke)
    }
}