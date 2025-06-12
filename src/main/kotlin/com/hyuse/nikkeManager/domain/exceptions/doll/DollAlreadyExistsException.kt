package com.hyuse.nikkeManager.domain.exceptions.doll

import com.hyuse.nikkeManager.domain.enums.Rarity
import com.hyuse.nikkeManager.domain.exceptions.nikke.NikkeException

class DollAlreadyExistsException(rarity: Rarity, level: Int): NikkeException("Doll Rarity: '$rarity' Level: '$level' already exists")