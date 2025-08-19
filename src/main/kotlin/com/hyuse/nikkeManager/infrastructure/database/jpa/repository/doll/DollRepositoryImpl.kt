package com.hyuse.nikkeManager.infrastructure.database.jpa.repository.doll

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.ports.DollRepository
import com.hyuse.nikkeManager.domain.vo.DollLevel
import com.hyuse.nikkeManager.infrastructure.database.jpa.mapper.toJpaEntity
import com.hyuse.nikkeManager.infrastructure.database.jpa.mapper.toModel
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class DollRepositoryImpl(
    private val dollJpaRepository: DollJpaRepository
) : DollRepository {

    override fun save(doll: Doll): Doll {

        val dollJpa = doll.toJpaEntity()

        val savedEntity = dollJpaRepository.save(dollJpa)

        return savedEntity.toModel()
    }

    override fun update(id: Int, doll: Doll): Doll {

        val dollJpa = doll.toJpaEntity()

        val savedEntity = dollJpaRepository.save(dollJpa)

        return savedEntity.toModel()
    }

    override fun delete(id: Int) {

        dollJpaRepository.deleteById(id)
    }

    override fun findAll(): Collection<Doll> {

        return dollJpaRepository.findAll().map { it.toModel() }.toMutableList()
    }

    override fun findById(id: Int): Optional<Doll> {

        return dollJpaRepository.findById(id).map { it.toModel() }
    }

    override fun findByRarityAndLevel(rarity: Rarity, level: DollLevel): Optional<Doll> {
        return dollJpaRepository.findByRarityAndLevel(rarity, level).map { it.toModel() }
    }

    override fun existById(id: Int): Boolean {

        return dollJpaRepository.existsById(id)
    }
}