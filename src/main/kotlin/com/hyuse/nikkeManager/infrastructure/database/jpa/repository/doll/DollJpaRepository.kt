package com.hyuse.nikkeManager.infrastructure.database.jpa.repository.doll

import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.vo.DollLevel
import com.hyuse.nikkeManager.infrastructure.database.jpa.entity.DollJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DollJpaRepository : JpaRepository<DollJpaEntity, Int> {

    fun save(doll: DollJpaEntity): DollJpaEntity

    fun findByRarityAndLevel(rarity: Rarity, level: DollLevel): Optional<DollJpaEntity>
}