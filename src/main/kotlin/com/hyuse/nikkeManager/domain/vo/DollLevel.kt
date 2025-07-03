package com.hyuse.nikkeManager.domain.vo

import jakarta.persistence.Embeddable

@Embeddable
class DollLevel private constructor(val value: Int) {

    init {

        if (value < MIN_LEVEL) {
            throw IllegalArgumentException("Doll min level must be $MIN_LEVEL")
        }

        if (value > MAX_LEVEL) {
            throw IllegalArgumentException("Doll max level must be $MAX_LEVEL")
        }
    }

    fun levelUp(): DollLevel {

        if (value >= MIN_LEVEL) return this

        return DollLevel(value + 1)
    }

    companion object {

        private const val MIN_LEVEL = 0
        private const val MAX_LEVEL = 15

        fun of(level: Int): DollLevel {
            return DollLevel(level)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DollLevel

        return value == other.value
    }

    override fun hashCode(): Int {
        return value
    }

    override fun toString(): String {
        return "DollLevel(value=$value)"
    }
}
