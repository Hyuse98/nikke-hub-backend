package com.hyuse.nikkeManager.repository

import com.hyuse.nikkeManager.enums.Rarity
import com.hyuse.nikkeManager.model.Doll
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface DollRepository : JpaRepository<Doll, Int> {
    fun findByRarityAndLevel(rarity: Rarity, level: Int): Optional<Doll>
}