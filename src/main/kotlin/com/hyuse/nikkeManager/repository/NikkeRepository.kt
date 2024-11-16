package com.hyuse.nikkeManager.repository

import com.hyuse.nikkeManager.enums.*
import com.hyuse.nikkeManager.model.Nikke
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface NikkeRepository : JpaRepository<Nikke, Int>, JpaSpecificationExecutor<Nikke> {

    fun findByName(name: String): Optional<Nikke>
    fun deleteByName(name: String)
}