package com.hyuse.nikkeManager.model

import com.hyuse.nikkeManager.enums.Rarity
import jakarta.persistence.*
import org.springframework.hateoas.RepresentationModel

@Entity
class Doll(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val rarity: Rarity,

    @Column(nullable = false)
    val level: Int
) : RepresentationModel<Doll>()