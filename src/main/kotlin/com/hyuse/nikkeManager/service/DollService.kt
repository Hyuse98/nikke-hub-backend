package com.hyuse.nikkeManager.service

import com.hyuse.nikkeManager.dto.DollDTO
import com.hyuse.nikkeManager.model.Doll
import com.hyuse.nikkeManager.repository.DollRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Transactional
@Service
class DollService(val dollRepository: DollRepository) {

    fun createDoll(dollDTO: DollDTO): Doll? {
        dollRepository.findByRarityAndLevel(dollDTO.rarity, dollDTO.level)?.let {
            throw IllegalStateException("Doll already registered for rarity ${dollDTO.rarity} and level ${dollDTO.level}")
        }
        return dollRepository.save(dollDTO.toModel())
    }
}