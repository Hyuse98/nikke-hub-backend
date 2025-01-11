package com.hyuse.nikkeManager.service

import com.hyuse.nikkeManager.dto.NikkeDTO
import com.hyuse.nikkeManager.enums.*
import com.hyuse.nikkeManager.model.Nikke
import com.hyuse.nikkeManager.repository.NikkeRepository
import com.hyuse.nikkeManager.repository.specifications.NikkeSpecifications
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class NikkeService(val nikkeRepository: NikkeRepository) {

    fun createNikke(nikkeDTO: NikkeDTO): Nikke {
        val nikkeExist = nikkeRepository.findNikkeByName(nikkeDTO.name)
        if (nikkeExist != null) {
            throw IllegalStateException("Nikke Already Exist")
        }
        return nikkeRepository.save(nikkeDTO.toModel())
    }

    fun updateNikke(nikkeDTO: NikkeDTO, name: String): Nikke {
        val nikkeExist = nikkeRepository.findNikkeByName(name)
            ?: throw IllegalStateException("Nikke not found")

        val updatedNikke = nikkeDTO.copy(id = nikkeExist.id).toModel()
        return nikkeRepository.save(updatedNikke)
    }

    fun updateNikke(nikkeDTO: NikkeDTO, id: Int): Nikke {
        val nikkeExist = nikkeRepository.findNikkeById(id)
            ?: throw IllegalStateException("Nikke not found")

        val nikke: Nikke = nikkeDTO.copy(id = nikkeExist.id).toModel()
        return nikkeRepository.save(nikke)
    }

    fun deleteNikke(name: String) {
        nikkeRepository.findNikkeByName(name) ?: throw IllegalStateException("Nikke not found")
        return nikkeRepository.deleteByName(name)
    }

    fun deleteNikke(id: Int) {
        nikkeRepository.findNikkeById(id) ?: throw IllegalStateException("Nikke not found")

        return nikkeRepository.deleteById(id)
    }

    fun listAllNikke(
        rarity: Rarity?,
        ownedStatus: OwnedStatus?,
        burstType: BurstType?,
        company: Company?,
        code: Code?,
        weapon: Weapon?,
        nikkeClass: NikkeClass?,
        cube: Cubes?
    ): List<Nikke> {
        val specification =
            NikkeSpecifications.byFilters(rarity, ownedStatus, burstType, company, code, weapon, nikkeClass, cube)
        return nikkeRepository.findAll(specification)
    }
}