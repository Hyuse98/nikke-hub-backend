package com.hyuse.nikkeManager.exception

class NikkeAlreadyExistsException (name: String): NikkeException("Nikke with name '$name' already exists")
