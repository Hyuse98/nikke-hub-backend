package com.hyuse.nikkeManager.repository

import com.hyuse.nikkeManager.model.Gear
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface GearRepository : JpaRepository<Gear, Int>{

    fun findByIdType(id: Int, type: String) : Optional<Gear>
}