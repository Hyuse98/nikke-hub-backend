package com.hyuse.nikkeManager.domain.exceptions.nikke

class NikkeAlreadyExistsException (name: String): NikkeException("Nikke with name $name already exists")