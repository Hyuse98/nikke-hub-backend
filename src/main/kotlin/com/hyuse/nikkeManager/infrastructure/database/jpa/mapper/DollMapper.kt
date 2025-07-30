package com.hyuse.nikkeManager.infrastructure.database.jpa.mapper

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.vo.DollLevel
import com.hyuse.nikkeManager.infrastructure.database.jpa.entity.DollJpaEntity

fun DollJpaEntity.toModel(): Doll {

    return Doll.reconstitute(
        id = this.id,
        rarity = this.rarity,
        level = DollLevel.of(this.level)
    )
}

fun Doll.toJpaEntity(): DollJpaEntity {

    return DollJpaEntity(
        id = this.id,
        rarity = this.rarity,
        level = this.level.value
    )
}