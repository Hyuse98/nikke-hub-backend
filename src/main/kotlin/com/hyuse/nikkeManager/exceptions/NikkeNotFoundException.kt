package com.hyuse.nikkeManager.exceptions

class NikkeNotFoundException(name: String): NikkeException("Nikke with name '$name' not found")