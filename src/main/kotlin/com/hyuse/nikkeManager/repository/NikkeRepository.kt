package com.hyuse.nikkeManager.repository

import com.hyuse.nikkeManager.model.Nikke
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface NikkeRepository : JpaRepository<Nikke, Int>, JpaSpecificationExecutor<Nikke> {

    @Query("SELECT n FROM Nikke n WHERE n.id = :id")
    fun findNikkeById(@Param("id")id: Int): Nikke?

    @Query("SELECT n FROM Nikke n WHERE n.name = :name")
    fun findNikkeByName(@Param("name") name: String): Nikke?

    @Modifying
    @Query("DELETE FROM Nikke n WHERE n.name = :name")
    fun deleteByName(@Param("name") name: String)

    override fun findAll(pageable: Pageable): Page<Nikke>

//    @Modifying
//    @Query("""
//        UPDATE Nikke n SET
//            n.core = :core,
//            n.attraction = :attraction,
//            n.skill1Level = :skill1Level,
//            n.skill2Level = :skill2Level,
//            n.burstLevel = :burstLevel,
//            n.rarity = :rarity,
//            n.ownedStatus = :ownedStatus,
//            n.burstType = :burstType,
//            n.company = :company,
//            n.code = :code,
//            n.weapon = :weapon,
//            n.nikkeClass = :nikkeClass,
//            n.cube = :cube,
//            n.doll = :doll
//        WHERE n.name = :name
//    """)
//    fun updateNikkeByName(
//        @Param("name") name: String,
//        @Param("core") core: Int,
//        @Param("attraction") attraction: Int,
//        @Param("skill1Level") skill1Level: Int,
//        @Param("skill2Level") skill2Level: Int,
//        @Param("burstLevel") burstLevel: Int,
//        @Param("rarity") rarity: Rarity,
//        @Param("ownedStatus") ownedStatus: OwnedStatus,
//        @Param("burstType") burstType: BurstType,
//        @Param("company") company: Company,
//        @Param("code") code: Code,
//        @Param("weapon") weapon: Weapon,
//        @Param("nikkeClass") nikkeClass: NikkeClass,
//        @Param("cube") cube: Cubes?,
//        @Param("doll") doll: Doll?
//    )
}