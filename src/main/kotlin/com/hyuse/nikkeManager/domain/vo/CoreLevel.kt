package com.hyuse.nikkeManager.domain.vo

import jakarta.persistence.Embeddable

@Embeddable
sealed interface CoreLevel{
    val value: Int

    fun levelUp(): CoreLevel

    companion object {

        const val CORE_MIN_LEVEL = 0
        const val CORE_MAX_LEVEL = 10

        fun of(level: Int): CoreLevel {
            return CoreLevelImpl(level)
        }
    }
}

private data class CoreLevelImpl(override val value: Int) : CoreLevel {

    init {

        if (value < CoreLevel.CORE_MIN_LEVEL) {
            throw IllegalArgumentException("Core min level must be ${CoreLevel.CORE_MIN_LEVEL}")
        }

        if (value > CoreLevel.CORE_MAX_LEVEL) {
            throw IllegalArgumentException("Core max level must be ${CoreLevel.CORE_MAX_LEVEL}")
        }
    }

    override fun levelUp(): CoreLevel {

        if (value >= CoreLevel.CORE_MAX_LEVEL) return this

        return CoreLevelImpl(value + 1)
    }
}
