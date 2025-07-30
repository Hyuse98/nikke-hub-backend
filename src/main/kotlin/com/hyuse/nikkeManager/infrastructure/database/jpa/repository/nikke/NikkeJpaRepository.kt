package com.hyuse.nikkeManager.infrastructure.database.jpa.repository.nikke

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.infrastructure.database.jpa.entity.NikkeJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface NikkeJpaRepository : JpaRepository<NikkeJpaEntity, Int>, JpaSpecificationExecutor<Nikke> {

    fun save(nikke: NikkeJpaEntity): NikkeJpaEntity

    fun deleteByName(name: String)

    override fun findById(id: Int): Optional<NikkeJpaEntity>

    fun findByName(name: String): Optional<NikkeJpaEntity>

    override fun findAll(): MutableList<NikkeJpaEntity>

    fun existsByName(name: String): Boolean
}