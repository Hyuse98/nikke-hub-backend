package com.hyuse.nikkeManager.domain.vo

data class CoreLevel private constructor(val value: Int) {

    init {

        if (value < CORE_MIN_LEVEL) {
            throw IllegalArgumentException("Core min level must be $CORE_MIN_LEVEL")
        }

        if (value > CORE_MAX_LEVEL) {
            throw IllegalArgumentException("Core max level must be $CORE_MAX_LEVEL")
        }
    }

    fun levelUp(): CoreLevel {

        if (value >= CORE_MAX_LEVEL) return this

        return CoreLevel(value + 1)
    }

    companion object {

        private const val CORE_MIN_LEVEL = 0
        private const val CORE_MAX_LEVEL = 10

        fun of(level: Int): CoreLevel {
            return CoreLevel(level)
        }
    }
}
