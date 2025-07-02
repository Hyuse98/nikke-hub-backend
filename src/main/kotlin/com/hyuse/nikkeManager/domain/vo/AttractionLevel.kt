package com.hyuse.nikkeManager.domain.vo

class AttractionLevel private constructor(val value: Int) {

    init {

        if (value < ATTRACTION_MIN_LEVEL) {
            throw IllegalArgumentException("Attraction min level must be $ATTRACTION_MIN_LEVEL")
        }

        if (value > ATTRACTION_MAX_LEVEL) {
            throw IllegalArgumentException("Attraction max leve must be $ATTRACTION_MAX_LEVEL")
        }
    }

    fun increment(): AttractionLevel {

        if (value >= ATTRACTION_MAX_LEVEL) return this

        return AttractionLevel(value + 1)
    }

    companion object {

        private const val ATTRACTION_MIN_LEVEL = 1
        private const val ATTRACTION_MAX_LEVEL = 30

        fun of(level: Int): AttractionLevel {
            return AttractionLevel(level)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AttractionLevel

        return value == other.value
    }

    override fun hashCode(): Int {
        return value
    }

    override fun toString(): String {
        return "AttractionLevel(value=$value)"
    }
}
