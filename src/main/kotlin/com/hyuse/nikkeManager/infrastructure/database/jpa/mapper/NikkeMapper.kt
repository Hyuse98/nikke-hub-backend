package com.hyuse.nikkeManager.infrastructure.database.jpa.mapper

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.infrastructure.database.jpa.entity.NikkeJpaEntity

fun NikkeJpaEntity.toModel(): Nikke {
    return Nikke.reconstitute(
        id = this.id,
        name = this.name,
        core = this.core,
        attraction = this.attraction,
        skill1 = this.skill1,
        skill2 = this.skill2,
        skillBurst = this.skillBurst,
        rarity = this.rarity,
        ownedStatus = this.ownedStatus,
        burstType = this.burstType,
        company = this.company,
        code = this.code,
        weapon = this.weapon,
        nikkeClass = this.nikkeClass,
        cube = this.cube
//            doll = this.doll,
    )
}

fun Nikke.toJpaEntity(): NikkeJpaEntity {
    return NikkeJpaEntity(
        id = this.id,
        name = this.name,
        core = this.core,
        attraction = this.attraction,
        skill1 = this.skill1,
        skill2 = this.skill2,
        skillBurst = this.skillBurst,
        rarity = this.rarity,
        ownedStatus = this.ownedStatus,
        burstType = this.burstType,
        company = this.company,
        code = this.code,
        weapon = this.weapon,
        nikkeClass = this.nikkeClass,
        cube = this.cube
//            doll = this.doll,
    )
}