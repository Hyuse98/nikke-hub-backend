package com.hyuse.nikkeManager.model

import jakarta.persistence.*

@Entity
class Gear(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int? = null,
    val nikkeId: Int,
    val type: String,
    val tier: String,
    val level: Int,
    val status1: String?,
    val status2: String?,
    val status3: String?,
    val value1: Double?,
    val value2: Double?,
    val value3: Double?
) {


}