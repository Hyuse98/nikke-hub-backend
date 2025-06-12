package com.hyuse.nikkeManager.infrastructure.database.jpa.repository

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.ports.DollRepository

class DollJpaRepositoryImpl(
    private val dollJpaRepository: DollJpaRepository
) : DollRepository {

    override fun save(doll: Doll): Doll {
        TODO("Not yet implemented")
    }

    override fun update(id: Int, doll: Doll): Doll {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun findById(id: Int): Doll {
        TODO("Not yet implemented")
    }

    override fun findAll(): Collection<Doll> {
        TODO("Not yet implemented")
    }

    override fun existById(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}