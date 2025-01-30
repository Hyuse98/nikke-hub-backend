package com.hyuse.nikkeManager.repository

import com.hyuse.nikkeManager.enums.Rarity
import com.hyuse.nikkeManager.model.Doll
import com.hyuse.nikkeManager.model.Nikke
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface DollRepository : JpaRepository<Doll, Int> {
    fun findByRarityAndLevel(rarity: Rarity, level: Int): Doll?
    override fun findAll(pageable: Pageable): Page<Doll>
}