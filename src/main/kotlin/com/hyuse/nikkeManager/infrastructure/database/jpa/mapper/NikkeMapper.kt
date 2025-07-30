package com.hyuse.nikkeManager.infrastructure.database.jpa.mapper

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.vo.AttractionLevel
import com.hyuse.nikkeManager.domain.vo.CharacterName
import com.hyuse.nikkeManager.domain.vo.CoreLevel
import com.hyuse.nikkeManager.domain.vo.SkillLevel
import com.hyuse.nikkeManager.infrastructure.database.jpa.entity.NikkeJpaEntity

fun NikkeJpaEntity.toModel(): Nikke {
    return Nikke.reconstitute(
        id = this.id,
        name = CharacterName.of(this.name),
        core = CoreLevel.of(this.core),
        attraction = AttractionLevel.of(this.attraction),
        skill1 = SkillLevel.of(this.skill1),
        skill2 = SkillLevel.of(this.skill2),
        skillBurst = SkillLevel.of(this.skillBurst),
        rarity = this.rarity,
        ownedStatus = this.ownedStatus,
        burstType = this.burstType,
        company = this.company,
        code = this.code,
        weapon = this.weapon,
        nikkeClass = this.nikkeClass,
        cube = this.cube
    )
}

fun Nikke.toJpaEntity(): NikkeJpaEntity {
    return NikkeJpaEntity(
        id = this.id,
        name = this.name.value,
        core = this.core.value,
        attraction = this.attraction.value,
        skill1 = this.skill1.value,
        skill2 = this.skill2.value,
        skillBurst = this.skillBurst.value,
        rarity = this.rarity,
        ownedStatus = this.ownedStatus,
        burstType = this.burstType,
        company = this.company,
        code = this.code,
        weapon = this.weapon,
        nikkeClass = this.nikkeClass,
        cube = this.cube
    )
}