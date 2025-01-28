package com.hyuse.nikkeManager.exception

import com.hyuse.nikkeManager.enums.Rarity

class DollNotFoundException : NikkeException {
    constructor(rarity: Rarity, level: Int) :
            super("Doll Rarity: '$rarity' Level: '$level' not found")

    constructor(id: Int) :
            super("Doll with ID '$id' not found")
}