package com.hyuse.nikkeManager.domain.vo

import jakarta.persistence.Embeddable

@Embeddable
sealed interface AttractionLevel{
    val value: Int

    fun increment(): AttractionLevel

    companion object {

        const val ATTRACTION_MIN_LEVEL = 1
        const val ATTRACTION_MAX_LEVEL = 30

        fun of(level: Int): AttractionLevel {
            return AttractionLevelImpl(level)
        }
    }
}

private data class AttractionLevelImpl (override val value: Int): AttractionLevel {

    init {

        if (value < AttractionLevel.ATTRACTION_MIN_LEVEL) {
            throw IllegalArgumentException("Attraction min level must be ${AttractionLevel.ATTRACTION_MIN_LEVEL}")
        }

        if (value > AttractionLevel.ATTRACTION_MAX_LEVEL) {
            throw IllegalArgumentException("Attraction max leve must be ${AttractionLevel.ATTRACTION_MAX_LEVEL}")
        }
    }

    override fun increment(): AttractionLevel {

        if (value >= AttractionLevel.ATTRACTION_MAX_LEVEL) return this

        return AttractionLevelImpl(value + 1)
    }

}
