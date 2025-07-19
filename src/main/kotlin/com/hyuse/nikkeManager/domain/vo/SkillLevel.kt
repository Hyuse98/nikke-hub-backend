package com.hyuse.nikkeManager.domain.vo

import jakarta.persistence.Embeddable

@Embeddable
sealed interface SkillLevel {
    val value: Int

    fun levelUp(): SkillLevel

    companion object {
        const val SKILL_MIN_LEVEL = 1
        const val SKILL_MAX_LEVEL = 10

        fun of(level: Int): SkillLevel {
            return SkillLevelImpl(level)
        }
    }
}

private data class SkillLevelImpl(override val value: Int) : SkillLevel {

    init {

        if (value < SkillLevel.SKILL_MIN_LEVEL) {
            throw IllegalArgumentException("Skill min level must be ${SkillLevel.SKILL_MIN_LEVEL}")
        }
        if (value > SkillLevel.SKILL_MAX_LEVEL) {
            throw IllegalArgumentException("Skill max level must be ${SkillLevel.SKILL_MAX_LEVEL}")
        }
    }

    override fun levelUp(): SkillLevel {
        if (value >= SkillLevel.SKILL_MAX_LEVEL) return this
        return SkillLevelImpl(value + 1)
    }
}