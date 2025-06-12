package com.hyuse.nikkeManager.infrastructure.database.jpa.entity

import com.hyuse.nikkeManager.domain.enums.Rarity
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

data class DollJpaEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "doll_id")
    val id: Int? = null,

    @Column(name = "rarity")
    val rarity: Rarity,

    @Column(name = "level")
    val level: Int
)