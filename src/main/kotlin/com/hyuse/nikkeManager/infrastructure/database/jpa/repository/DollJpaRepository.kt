package com.hyuse.nikkeManager.infrastructure.database.jpa.repository

import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.infrastructure.database.jpa.entity.DollJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface DollJpaRepository : JpaRepository<Doll, Int> {

    fun save (doll: DollJpaEntity): Doll

    fun update (doll: DollJpaEntity): Doll

    fun findDollById(id: Int): Doll?

    fun findByRarityAndLevel(rarity: Rarity, level: Int): Doll?

    override fun findAll(pageable: Pageable): Page<Doll>

}