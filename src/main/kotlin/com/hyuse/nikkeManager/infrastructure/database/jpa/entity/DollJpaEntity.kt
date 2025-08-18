package com.hyuse.nikkeManager.infrastructure.database.jpa.entity

import com.hyuse.nikkeManager.domain.enums.Rarity
import jakarta.persistence.*

@Entity
@Table(name = "doll")
class DollJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "doll_id")
    val id: Int? = null,

    @Column(name = "rarity")
    @Enumerated(value = EnumType.STRING)
    val rarity: Rarity,

    @Column(name = "level")
    val level: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DollJpaEntity

        if (id != other.id) return false
        if (rarity != other.rarity && level != other.level) return false

        return true
    }

    override fun hashCode(): Int {
        return id ?: 0
    }

    override fun toString(): String {
        return "DollJpaEntity(id=$id, rarity=$rarity, level=$level)"
    }
}