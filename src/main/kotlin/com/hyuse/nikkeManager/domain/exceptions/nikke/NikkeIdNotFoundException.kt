package com.hyuse.nikkeManager.domain.exceptions.nikke

class NikkeIdNotFoundException(id: String) : NikkeException("Nikke with id '$id' not found")