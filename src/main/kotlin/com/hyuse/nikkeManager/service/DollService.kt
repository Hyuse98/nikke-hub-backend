package com.hyuse.nikkeManager.service

import com.hyuse.nikkeManager.dto.DollDTO
import com.hyuse.nikkeManager.enums.Rarity
import com.hyuse.nikkeManager.exception.DollAlreadyExistsException
import com.hyuse.nikkeManager.exception.DollNotFoundException
import com.hyuse.nikkeManager.model.Doll
import com.hyuse.nikkeManager.repository.DollRepository
import jakarta.transaction.Transactional
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

    fun listDolls(): List<Doll> {
        return dollRepository.findAll();
    }

    fun searchDoll(rarity: Rarity, level: Int): Doll? {
        return dollRepository.findByRarityAndLevel(rarity, level)
    }

    fun deleteDollById(id: Int) {
        val doll = dollRepository.findById(id).orElseThrow { DollNotFoundException(id) }
        dollRepository.deleteById(id)
    }

}