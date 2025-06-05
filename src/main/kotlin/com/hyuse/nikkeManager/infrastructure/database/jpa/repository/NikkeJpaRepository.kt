package com.hyuse.nikkeManager.infrastructure.database.jpa.repository

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.infrastructure.database.jpa.entity.NikkeJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface NikkeJpaRepository : JpaRepository<NikkeJpaEntity, Int>, JpaSpecificationExecutor<Nikke> {

    fun save(nikke: NikkeJpaEntity): Nikke

    fun update(nikke: NikkeJpaEntity): Nikke

    @Modifying
    @Query("DELETE FROM Nikke n WHERE n.name = :name")
    fun deleteByName(@Param("name") name: String)

    @Query("SELECT n FROM Nikke n WHERE n.id = :id")
    override fun findById(@Param("id") id: Int): Optional<NikkeJpaEntity>

    @Query("SELECT n FROM Nikke n WHERE n.name = :name")
    fun findByName(@Param("name") name: String): Optional<Nikke>

    override fun findAll(): MutableList<NikkeJpaEntity>

    fun existsByName(name: String): Boolean
}