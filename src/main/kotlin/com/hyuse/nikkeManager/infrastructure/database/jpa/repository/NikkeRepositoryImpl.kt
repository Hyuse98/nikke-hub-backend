package com.hyuse.nikkeManager.infrastructure.database.jpa.repository

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.ports.NikkeRepository
import com.hyuse.nikkeManager.infrastructure.database.jpa.mapper.toJpaEntity
import com.hyuse.nikkeManager.infrastructure.database.jpa.mapper.toModel
import org.springframework.stereotype.Repository

@Repository
class NikkeRepositoryImpl(
    private val nikkeJpaRepository: NikkeJpaRepository
) : NikkeRepository {

    override fun save(nikke: Nikke): Nikke {
        return nikkeJpaRepository.save(nikke.toJpaEntity())
    }

    override fun update(nikke: Nikke): Nikke {
        return nikkeJpaRepository.update(nikke.toJpaEntity())
    }

    override fun delete(id: Int) {
        nikkeJpaRepository.deleteById(id)
    }

    override fun findByName(name: String): Nikke {
        return nikkeJpaRepository.findByName(name).get()
    }

    override fun findById(id: Int): Nikke {
        return nikkeJpaRepository.findById(id).get().toModel()
    }

    override fun findAll(): MutableList<Nikke> {
        return nikkeJpaRepository.findAll().map { it.toModel() }.toMutableList()
    }

    override fun existsById(id: Int): Boolean {
        return nikkeJpaRepository.existsById(id)
    }

    override fun existsByName(name: String): Boolean {
        return nikkeJpaRepository.existsByName(name)
    }
}

