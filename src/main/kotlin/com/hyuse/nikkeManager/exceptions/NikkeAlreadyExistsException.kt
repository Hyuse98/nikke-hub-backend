package com.hyuse.nikkeManager.exceptions

class NikkeAlreadyExistsException (name: String): NikkeException("Nikke with name '$name' already exists")