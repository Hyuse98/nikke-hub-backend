package com.hyuse.nikkeManager.exception

class NikkeNotFoundException(name: String): NikkeException("Nikke with name '$name' not found")