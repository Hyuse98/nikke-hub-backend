package com.hyuse.nikkeManager.service

import com.hyuse.nikkeManager.dto.DollDTO
import com.hyuse.nikkeManager.enums.Rarity
import com.hyuse.nikkeManager.exceptions.DollAlreadyExistsException
import com.hyuse.nikkeManager.exceptions.DollNotFoundException
import com.hyuse.nikkeManager.model.Doll
import com.hyuse.nikkeManager.repository.DollRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Transactional
@Service
class DollService(val dollRepository: DollRepository) {

    fun createDoll(dollDTO: DollDTO): Doll? {
        dollRepository.findByRarityAndLevel(dollDTO.rarity, dollDTO.level)?.let {
            throw DollAlreadyExistsException(dollDTO.rarity, dollDTO.level)
        }
        return dollRepository.save(dollDTO.toModel())
    }

    fun getListDolls(pageable: Pageable): Page<Doll> {
        return dollRepository.findAll(pageable);
    }

    fun getDollByRarityAndLevel(rarity: Rarity, level: Int): Doll? {
        val doll = dollRepository.findByRarityAndLevel(rarity, level) ?: throw DollNotFoundException(rarity, level)
        return doll
    }

    fun deleteDollById(id: Int) {
        dollRepository.findDollById(id) ?: throw DollNotFoundException(id)
        dollRepository.deleteById(id)
    }
}