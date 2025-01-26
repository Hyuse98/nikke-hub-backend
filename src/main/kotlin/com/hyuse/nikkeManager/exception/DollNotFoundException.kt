package com.hyuse.nikkeManager.exception

import com.hyuse.nikkeManager.enums.Rarity

class DollNotFoundException(rarity: Rarity, level: Int): NikkeException("Doll Rarity: '$rarity' Level: '$level' not found")