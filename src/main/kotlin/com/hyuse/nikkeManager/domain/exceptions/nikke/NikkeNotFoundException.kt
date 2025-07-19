package com.hyuse.nikkeManager.domain.exceptions.nikke

class NikkeNotFoundException : NikkeException {
    constructor (name: String) :
            super("Nikke with name $name not found")
    constructor(id: Int):
            super("Nikke with id $id not found")
}
