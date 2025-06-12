package com.hyuse.nikkeManager.domain.exceptions.doll

import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeException

class DollNotFoundException : NikkeException {
    constructor(rarity: Rarity, level: Int) :
            super("Doll Rarity: '$rarity' Level: '$level' not found")

    constructor(id: Int) :
            super("Doll with ID '$id' not found")
}