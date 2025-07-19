package com.hyuse.nikkeManager.domain.vo

import jakarta.persistence.Embeddable

@Embeddable
sealed interface CharacterName{
    val value: String

    companion object {

        const val NAME_MAX_LENGTH = 50

        fun of(name: String): CharacterName {
            return CharacterNameImpl(name)
        }
    }

}

private data class CharacterNameImpl(override val value: String) : CharacterName {

    init {

        if (value.isBlank()) {
            throw IllegalArgumentException("Nikke name cant be blank")
        }

        if (value.length > CharacterName.NAME_MAX_LENGTH) {
            throw IllegalArgumentException("Nikke name is too long, cant be greater than ${CharacterName.NAME_MAX_LENGTH} characters")
        }
    }
}
