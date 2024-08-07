package com.hyuse.nikkeManager.repository

import com.hyuse.nikkeManager.model.Nikke
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface NikkeRepository : JpaRepository<Nikke, Int>{
    fun findByName(name:String): Optional<Nikke>
}