package com.hyuse.nikkeManager.infrastructure.database.jpa.entity

import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.vo.DollLevel
import jakarta.persistence.*

@Entity
class DollJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "doll_id")
    val id: Int? = null,

    @Column(name = "rarity")
    val rarity: Rarity,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "doll_level"))
    val level: DollLevel
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DollJpaEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return id ?: 0
    }

    override fun toString(): String {
        return "DollJpaEntity(id=$id, rarity=$rarity, level=$level)"
    }
}