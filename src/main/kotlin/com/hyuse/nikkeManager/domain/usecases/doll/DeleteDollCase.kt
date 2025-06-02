package com.hyuse.nikkeManager.domain.usecases.doll

import com.hyuse.nikkeManager.domain.ports.DollRepository

class DeleteDollCase (
    private val dollRepository: DollRepository
){

    fun execute(id: Int): Boolean{

        val existingDoll = dollRepository.existById(id)

        if(existingDoll) throw Exception("Nikke already Exist")

        val isDone: Boolean = dollRepository.delete(id)

        return isDone
    }

}