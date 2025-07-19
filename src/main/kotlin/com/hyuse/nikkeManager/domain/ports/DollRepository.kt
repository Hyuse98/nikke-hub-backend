package com.hyuse.nikkeManager.domain.ports

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.vo.DollLevel
import java.util.*

interface DollRepository {

    fun save(doll: Doll): Doll
    fun update(id: Int, doll: Doll): Doll
    fun delete(id: Int)

    fun findAll(): Collection<Doll>
    fun findById(id: Int): Optional<Doll>
    fun findByRarityAndLevel(rarity: Rarity, level: DollLevel): Optional<Doll>

    fun existById(id: Int): Boolean
}
