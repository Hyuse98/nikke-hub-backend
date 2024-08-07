package com.hyuse.nikkeManager.service

import com.hyuse.nikkeManager.DTO.NikkeDTO
import com.hyuse.nikkeManager.model.Nikke
import com.hyuse.nikkeManager.repository.NikkeRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service

@Transactional
@Service
class NikkeService (val nikkeRepository: NikkeRepository){

    fun addNikke(nikkeDTO: NikkeDTO): Nikke {
        val nikkeExist = nikkeRepository.findByName(nikkeDTO.name)
        if(nikkeExist.isPresent){
            throw IllegalStateException("Nikke Already Registered")
        }
        return nikkeRepository.save(nikkeDTO.toModel())
    }
//TODO
}