package com.hyuse.nikkeManager.domain.vo

data class CharacterName private constructor(val value: String) {

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
}
