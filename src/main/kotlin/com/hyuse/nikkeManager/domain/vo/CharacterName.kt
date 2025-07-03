package com.hyuse.nikkeManager.domain.vo

import jakarta.persistence.Embeddable

@Embeddable
class CharacterName private constructor(val value: String) {

    init {

        if (value.isBlank()) {
            throw IllegalArgumentException("Nikke name cant be blank")
        }

        if (value.length > NAME_MAX_LENGTH) {
            throw IllegalArgumentException("Nikke name is too long, cant be greater than $NAME_MAX_LENGTH characters")
        }
    }

    companion object {

        private const val NAME_MAX_LENGTH = 50

        fun of(name: String): CharacterName {
            return CharacterName(name)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CharacterName

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "CharacterName(value='$value')"
    }
}
