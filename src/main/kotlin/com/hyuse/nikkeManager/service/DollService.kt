package com.hyuse.nikkeManager.service

import com.hyuse.nikkeManager.DTO.DollDTO
import com.hyuse.nikkeManager.model.Doll
import com.hyuse.nikkeManager.repository.DollRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Transactional
@Service
class DollService(val dollRepository: DollRepository) {

    fun addDoll(dollDTO: DollDTO): Doll {
        val dollExists = dollRepository.findByRarityAndLevel(dollDTO.rarity, dollDTO.level)
        if (dollExists.isPresent) {
            throw IllegalStateException("Ja tem")
        }
        return dollRepository.save(dollDTO.toModel())
    }
}