package com.hyuse.nikkeManager.service

import com.hyuse.nikkeManager.dto.NikkeDTO
import com.hyuse.nikkeManager.enums.*
import com.hyuse.nikkeManager.exceptions.NikkeAlreadyExistsException
import com.hyuse.nikkeManager.exceptions.NikkeIdNotFoundException
import com.hyuse.nikkeManager.exceptions.NikkeNotFoundException
import com.hyuse.nikkeManager.model.Nikke
import com.hyuse.nikkeManager.repository.NikkeRepository
import com.hyuse.nikkeManager.repository.specifications.NikkeSpecifications
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class NikkeService(val nikkeRepository: NikkeRepository) {

    @CacheEvict(value = ["nikkes"], allEntries = true)
    fun createNikke(nikkeDTO: NikkeDTO): Nikke {
        val nikkeExist = nikkeRepository.findNikkeByName(nikkeDTO.name)
        if (nikkeExist != null) {
            throw NikkeAlreadyExistsException(nikkeExist.name)
        }
        return nikkeRepository.save(nikkeDTO.toModel())
    }

    @CacheEvict(value = ["nikkes"], allEntries = true)
    fun updateNikke(nikkeDTO: NikkeDTO, name: String): Nikke {
        val nikkeExist = nikkeRepository.findNikkeByName(name)
            ?: throw NikkeNotFoundException(name)

        val updatedNikke = nikkeDTO.copy(id = nikkeExist.id).toModel()
        return nikkeRepository.save(updatedNikke)
    }

    @CacheEvict(value = ["nikkes"], allEntries = true)
    fun updateNikke(nikkeDTO: NikkeDTO, id: Int): Nikke {
        val nikkeExist = nikkeRepository.findNikkeById(id)
            ?: throw NikkeIdNotFoundException("$id")

        val nikke: Nikke = nikkeDTO.copy(id = nikkeExist.id).toModel()
        return nikkeRepository.save(nikke)
    }

    @CacheEvict(value = ["nikkes"], allEntries = true)
    fun deleteNikke(name: String) {
        nikkeRepository.findNikkeByName(name) ?: throw NikkeNotFoundException(name)
        return nikkeRepository.deleteByName(name)
    }

    @CacheEvict(value = ["nikkes"], allEntries = true)
    fun deleteNikke(id: Int) {
        nikkeRepository.findNikkeById(id) ?: throw NikkeIdNotFoundException("$id")

        return nikkeRepository.deleteById(id)
    }

    @Cacheable("nikkes")
    fun listAllNikke(pageable: Pageable): Page<Nikke> = nikkeRepository.findAll(pageable)

    @Cacheable("nikkes")
    fun listAllNikkeFiltered(
        rarity: Rarity?,
        ownedStatus: OwnedStatus?,
        burstType: BurstType?,
        company: Company?,
        code: Code?,
        weapon: Weapon?,
        nikkeClass: NikkeClass?,
        cube: Cubes?,
        pageable: Pageable
    ): Page<Nikke> {
        val specification =
            NikkeSpecifications.byFilters(rarity, ownedStatus, burstType, company, code, weapon, nikkeClass, cube)
        return nikkeRepository.findAll(specification, pageable)
    }

    fun searchNikke(name: String): Nikke?{
        return nikkeRepository.findNikkeByName(name)
    }
}