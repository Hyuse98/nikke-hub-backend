package com.hyuse.nikkeManager.service

import com.hyuse.nikkeManager.dto.NikkeDTO
import com.hyuse.nikkeManager.enums.*
import com.hyuse.nikkeManager.model.Nikke
import com.hyuse.nikkeManager.repository.DollRepository
import com.hyuse.nikkeManager.repository.NikkeRepository
import com.hyuse.nikkeManager.repository.specifications.NikkeSpecifications
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class NikkeService(val nikkeRepository: NikkeRepository, val dollRepository: DollRepository) {

    fun createNikke(nikkeDTO: NikkeDTO): Nikke {
        val nikkeExist = nikkeRepository.findByName(nikkeDTO.name)
        if (nikkeExist.isPresent) {
            throw IllegalStateException("Nikke Already Registered")
        }
        return nikkeRepository.save(nikkeDTO.toModel())
    }

    fun updateNikke(nikkeDTO: NikkeDTO, name: String): Nikke {
        val nikkeExist = nikkeRepository.findByName(name)
        if (nikkeExist.isPresent) {
            val existingNikke = nikkeExist.get()
            val updatedNikke = nikkeDTO.copy(id = existingNikke.id).toModel()
            return nikkeRepository.save(updatedNikke)
        }
        throw IllegalStateException("Nikke not found")
    }

    fun updateNikke(nikkeDTO: NikkeDTO, id: Int): Nikke {
        val nikkeExist = nikkeRepository.findById(id)
        if (nikkeExist.isPresent) {
            val nikke: Nikke = nikkeDTO.copy(id = id).toModel()
            return nikkeRepository.save(nikke)
        }
        throw IllegalStateException("Nikke not found")
    }

    fun deleteNikke(name: String) {
        return nikkeRepository.deleteByName(name)
    }

    fun deleteNikke(id: Int) {
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
        val specification = NikkeSpecifications.byFilters(rarity, ownedStatus, burstType, company, code, weapon, nikkeClass, cube)
        return nikkeRepository.findAll(specification)
    }
}