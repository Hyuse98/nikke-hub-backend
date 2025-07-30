package com.hyuse.nikkeManager.infrastructure.database.jpa.repository.doll

import com.hyuse.nikkeManager.infrastructure.database.jpa.entity.DollJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DollJpaRepository : JpaRepository<DollJpaEntity, Int> {

    fun save(doll: DollJpaEntity): DollJpaEntity

    fun findByRarityAndLevel(doll: DollJpaEntity): MutableList<DollJpaEntity>
}