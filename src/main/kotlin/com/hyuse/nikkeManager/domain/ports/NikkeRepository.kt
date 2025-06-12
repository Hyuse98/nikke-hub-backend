package com.hyuse.nikkeManager.domain.ports

import com.hyuse.nikkeManager.domain.entities.Nikke

interface NikkeRepository {

    fun save(nikke: Nikke): Nikke
    fun update(nikke: Nikke): Nikke
    fun delete(id: Int)

    fun findByName(name: String): Nikke
    fun findById(id: Int): Nikke

    fun findAll(): MutableList<Nikke>

    fun existsById(id: Int): Boolean
    fun existsByName(name: String): Boolean
}