package com.hyuse.nikkeManager.infrastructure.database.jpa.repository.nikke

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.ports.NikkeRepository
import com.hyuse.nikkeManager.infrastructure.database.jpa.mapper.toJpaEntity
import com.hyuse.nikkeManager.infrastructure.database.jpa.mapper.toModel
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class NikkeRepositoryImpl(
    private val nikkeJpaRepository: NikkeJpaRepository
) : NikkeRepository {

    override fun save(nikke: Nikke): Nikke {

        val nikkeJpa = nikke.toJpaEntity()

        val savedEntity = nikkeJpaRepository.save(nikkeJpa)

        return savedEntity.toModel()
    }

    override fun update(nikke: Nikke): Nikke {

        val nikkeJpa = nikke.toJpaEntity()

        val savedEntity = nikkeJpaRepository.save(nikkeJpa)

        return savedEntity.toModel()
    }

    override fun delete(id: Int) {
        nikkeJpaRepository.deleteById(id)
    }

    override fun findByName(name: String): Optional<Nikke> {
        val nikkeJpaOptional = nikkeJpaRepository.findByName(name)
        return nikkeJpaOptional.map { it.toModel() }
    }

    override fun findById(id: Int): Optional<Nikke> {
        return nikkeJpaRepository.findById(id).map { it.toModel() }
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

