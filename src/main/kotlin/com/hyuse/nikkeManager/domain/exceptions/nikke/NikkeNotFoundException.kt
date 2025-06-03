package com.hyuse.nikkeManager.domain.exceptions.nikke

class NikkeNotFoundException(name: String): NikkeException("Nikke with name '$name' not found")