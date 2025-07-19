package com.hyuse.nikkeManager.domain.exceptions.doll

import com.hyuse.nikkeManager.domain.enums.Rarity

class DollAlreadyExistsException(rarity: Rarity, level: Int) : DollException("Doll with Rarity $rarity and Level $level already exists")