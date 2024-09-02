package com.hyuse.nikkeManager.service

import com.hyuse.nikkeManager.DTO.GearDTO
import com.hyuse.nikkeManager.model.Gear
import com.hyuse.nikkeManager.repository.GearRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.ResponseBody

@Service
@Transactional
class GearService (val gearRepository: GearRepository) {

    fun createGear(gearDTO: GearDTO): Gear {



        return gearRepository.save(gearDTO.toModel())
    }
}