package com.hyuse.nikkeManager.model

import com.hyuse.nikkeManager.enums.Rarity
import jakarta.persistence.*

@Entity
class Doll (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int? = null,

    @Enumerated(EnumType.STRING)
    val rarity: Rarity,

    val level: Int
){
}