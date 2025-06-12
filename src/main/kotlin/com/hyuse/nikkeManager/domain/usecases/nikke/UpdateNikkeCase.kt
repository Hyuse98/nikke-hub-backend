package com.hyuse.nikkeManager.domain.usecases.nikke

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.enums.*
import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeNotFoundException
import com.hyuse.nikkeManager.domain.ports.NikkeRepository

class UpdateNikkeCase(
    private val nikkeRepository: NikkeRepository
) {

    data class Input(
        val id: Int? = null,
        val name: String,
        val core: Int,
        val attraction: Int,
        val skill1: Int,
        val skill2: Int,
        val skillBurst: Int,
        val rarity: Rarity,
        val ownedStatus: OwnedStatus,
        val burstType: BurstType,
        val company: Company,
        val code: Code,
        val weapon: Weapon,
        val nikkeClass: NikkeClass,
        val cube: Cubes? = null,
//    val doll: Doll? = null
    )

    fun execute(id: Int, input: Input): Nikke {

        val existingNikke = nikkeRepository.existsById(id)

        if (!existingNikke) throw NikkeNotFoundException("Nikke with id $id not exists!")

        val updatedNikke = Nikke(
            id = id,
            name = input.name,
            core = input.core,
            attraction = input.attraction,
            skill1 = input.skill1,
            skill2 = input.skill2,
            skillBurst = input.skillBurst,
            rarity = input.rarity,
            ownedStatus = input.ownedStatus,
            burstType = input.burstType,
            company = input.company,
            code = input.code,
            weapon = input.weapon,
            nikkeClass = input.nikkeClass,
            cube = input.cube
//            doll = input.doll
        )

        return nikkeRepository.update(updatedNikke)
    }

}