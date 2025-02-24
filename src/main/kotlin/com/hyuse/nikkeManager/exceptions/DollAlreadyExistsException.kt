package com.hyuse.nikkeManager.exceptions

import com.hyuse.nikkeManager.enums.Rarity

class DollAlreadyExistsException(rarity: Rarity, level: Int): NikkeException("Doll Rarity: '$rarity' Level: '$level' already exists")