package com.hyuse.nikkeManager.domain.vo

import jakarta.persistence.Embeddable

@Embeddable
class SkillLevel private constructor(val value: Int) {

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SkillLevel

        return value == other.value
    }

    override fun hashCode(): Int {
        return value
    }

    override fun toString(): String {
        return "SkillLevel(value=$value)"
    }
}
