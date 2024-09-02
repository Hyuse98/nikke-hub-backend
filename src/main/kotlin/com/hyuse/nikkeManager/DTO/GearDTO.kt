package com.hyuse.nikkeManager.DTO

import com.hyuse.nikkeManager.model.Gear

class GearDTO(
    val type: String,
    val nikkeId: Int,
    val tier: String,
    val level: Int,
    val status1: String?,
    val status2: String?,
    val status3: String?,
    val value1: Double?,
    val value2: Double?,
    val value3: Double?
) {
    fun toModel(): Gear{
        val gear = Gear(
            nikkeId = this.nikkeId,
            type = this.type,
            tier = this.tier,
            level = this.level,
            status1 = this.status1,
            status2 = this.status2,
            status3 = this.status3,
            value1 = this.value1,
            value2 = this.value2,
            value3 = this.value3
        )
        return gear
    }
}