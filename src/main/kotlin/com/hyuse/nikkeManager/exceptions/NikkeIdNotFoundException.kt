package com.hyuse.nikkeManager.exceptions

class NikkeIdNotFoundException(id: String) : NikkeException("Nikke with id '$id' not found")