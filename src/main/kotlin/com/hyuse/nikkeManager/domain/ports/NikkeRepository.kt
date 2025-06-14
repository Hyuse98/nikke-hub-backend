package com.hyuse.nikkeManager.domain.ports

import com.hyuse.nikkeManager.domain.entities.Nikke
import java.util.Optional

interface NikkeRepository {

    fun save(nikke: Nikke): Nikke
    fun update(nikke: Nikke): Nikke
    fun delete(id: Int)

    fun findByName(name: String): Optional<Nikke>
    fun findById(id: Int): Optional<Nikke>

    fun findAll(): MutableList<Nikke>

    fun existsById(id: Int): Boolean
    fun existsByName(name: String): Boolean
}