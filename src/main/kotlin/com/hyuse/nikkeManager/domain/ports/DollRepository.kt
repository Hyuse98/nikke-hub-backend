package com.hyuse.nikkeManager.domain.ports

import com.hyuse.nikkeManager.domain.entities.Doll
import java.util.*

interface DollRepository {

    fun save(doll: Doll): Doll
    fun update(id: Int, doll: Doll): Doll
    fun delete(id: Int): Boolean

    fun findById(id: Int): Optional<Doll>
//    fun findByRarity(rarity: Rarity): Optional<Doll>
//    fun findByLevel(level: Int): Doll

    fun findAll(): Collection<Doll>

    //TODO Extra use cases
    /*
    * existByRarity
    * existByLevel
    *
    */

    fun existById(id: Int): Boolean

}
