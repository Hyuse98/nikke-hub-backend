package com.hyuse.nikkeManager.exception

class NikkeIdNotFoundException(id: String) : NikkeException("Nikke with id '$id' not found")