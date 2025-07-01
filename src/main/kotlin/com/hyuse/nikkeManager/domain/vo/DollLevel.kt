package com.hyuse.nikkeManager.domain.vo

data class DollLevel private constructor(val value: Int){

    init {

        if (value < MIN_LEVEL){
            throw IllegalArgumentException("Doll min level must be $MIN_LEVEL")
        }

        if (value > MAX_LEVEL){
            throw IllegalArgumentException("Doll max level must be $MAX_LEVEL")
        }
    }

    companion object {

        private const val MIN_LEVEL = 0
        private const val MAX_LEVEL = 15

        fun of(level: Int): DollLevel{
            return DollLevel(level)
        }
    }
}
