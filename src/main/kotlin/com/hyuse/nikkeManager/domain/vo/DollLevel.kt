package com.hyuse.nikkeManager.domain.vo

import jakarta.persistence.Embeddable

@Embeddable
sealed interface DollLevel {
    val value: Int

    fun levelUp(): DollLevel

    companion object {
        const val MIN_LEVEL = 0
        const val MAX_LEVEL = 15

        fun of(level: Int): DollLevel {
            return DollLevelImpl(level)
        }
    }
}

private data class DollLevelImpl(override val value: Int) : DollLevel {

    init {

        if (value < DollLevel.MIN_LEVEL) {
            throw IllegalArgumentException("Doll min level must be ${DollLevel.MIN_LEVEL}")
        }

        if (value > DollLevel.MAX_LEVEL) {
            throw IllegalArgumentException("Doll max level must be ${DollLevel.MAX_LEVEL}")
        }
    }

    override fun levelUp(): DollLevel {
        if (value >= DollLevel.MAX_LEVEL) return this
        return DollLevelImpl(value + 1)
    }
}
