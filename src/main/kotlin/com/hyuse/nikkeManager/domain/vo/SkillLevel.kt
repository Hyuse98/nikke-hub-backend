package com.hyuse.nikkeManager.domain.vo

data class SkillLevel private constructor(val value: Int) {

    init {

        if (value < SKILL_MIN_LEVEL) {
            throw IllegalArgumentException("Skill min level must be $SKILL_MIN_LEVEL")
        }

        if (value > SKILL_MAX_LEVEL) {
            throw IllegalArgumentException("Skill max level must be $SKILL_MAX_LEVEL")
        }
    }

    fun levelUp(): SkillLevel {

        if (value >= SKILL_MAX_LEVEL) return this

        return SkillLevel(value + 1)
    }

    companion object {

        private const val SKILL_MIN_LEVEL = 1
        private const val SKILL_MAX_LEVEL = 10

        fun of(level: Int): SkillLevel {
            return SkillLevel(level)
        }
    }
}
