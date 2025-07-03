package com.hyuse.nikkeManager.infrastructure.database.jpa.repository

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.ports.DollRepository
import com.hyuse.nikkeManager.infrastructure.database.jpa.mapper.toJpaEntity
import java.util.*

class DollJpaRepositoryImpl(
    private val dollJpaRepository: DollJpaRepository
) : DollRepository {

    override fun save(doll: Doll): Doll {
        return dollJpaRepository.save(doll)
    }

    override fun update(id: Int, doll: Doll): Doll {
        return dollJpaRepository.update(doll.toJpaEntity())
    }

    override fun delete(id: Int) {
        return dollJpaRepository.deleteById(id)
    }

    override fun findById(id: Int): Optional<Doll> {
        return dollJpaRepository.findById(id)
    }

    override fun findAll(): Collection<Doll> {
        return dollJpaRepository.findAll()
    }

    override fun existById(id: Int): Boolean {
        return dollJpaRepository.existsById(id)
    }
}