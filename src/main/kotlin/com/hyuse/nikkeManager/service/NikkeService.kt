package com.hyuse.nikkeManager.service

import com.hyuse.nikkeManager.DTO.NikkeDTO
import com.hyuse.nikkeManager.model.Nikke
import com.hyuse.nikkeManager.repository.DollRepository
import com.hyuse.nikkeManager.repository.NikkeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class NikkeService(val nikkeRepository: NikkeRepository, val dollRepository: DollRepository) {

        fun createNikke(nikkeDTO: NikkeDTO): Nikke {
        val nikkeExist = nikkeRepository.findByName(nikkeDTO.name)
        if(nikkeExist.isPresent){
            throw IllegalStateException("Nikke Already Registered")
        }
        return nikkeRepository.save(nikkeDTO.toModel())
    }
//    fun createNikke(nikkeDTO: NikkeDTO): Nikke {
//        val nikkeExist = nikkeRepository.findByName(nikkeDTO.name)
//        val dollExist = dollRepository.findById(nikkeDTO.dollId)
//
//        if(nikkeExist.isPresent){
//            throw IllegalStateException("ja tem")
//        }
//
//        if(dollExist.isPresent) {
//            return nikkeRepository.save(nikkeDTO.toModel())
//        }
//
//
//
//        return nikkeDTO.toModel()
//    }

    fun updateNikke(nikkeDTO: NikkeDTO, id: Int): Nikke {
        val nikkeExist = nikkeRepository.findById(id)
        if (nikkeExist.isPresent) {
            val nikke: Nikke = nikkeDTO.copy(id = id).toModel()
            return nikkeRepository.save(nikke)
        }
        return throw IllegalStateException("fds")
    }

//TODO
}