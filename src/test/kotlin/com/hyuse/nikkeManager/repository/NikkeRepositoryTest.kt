package com.hyuse.nikkeManager.repository

import com.hyuse.nikkeManager.enums.*
import com.hyuse.nikkeManager.model.Nikke
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
class NikkeRepositoryTest(
) {

    @Autowired
    private lateinit var nikkeRepository: NikkeRepository

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Test
    @DisplayName("Should find a nikke by name")
    fun findNikkeByNameSuccess() {

        val nikke = Nikke(
            id = null,
            name = "Test",
            core = 1,
            attraction = 1,
            skill1Level = 1,
            skill2Level = 1,
            burstLevel = 1,
            rarity = Rarity.SSR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.PILGRIM,
            code = Code.ELECTRIC,
            weapon = Weapon.SR,
            nikkeClass = NikkeClass.ATTACKER,
            cube = null,
            doll = null
        )
        entityManager.persist(nikke)
        entityManager.flush()

        val name = "Test"
        val result = nikkeRepository.findNikkeByName(name)

        assertThat(result).isNotNull
        assertThat(result?.name).isEqualTo(name)
    }

    @Test
    @DisplayName("Should not find a nikke by name")
    fun findNikkeByNameFail() {

        nikkeRepository.deleteAll()

        val name = "Test"
        val result = nikkeRepository.findNikkeByName(name)

        assertThat(result).isNull()
    }

    @Test
    @DisplayName("Should delete a nikke by name")
    fun deleteByNameSuccess() {

        val nikke = Nikke(
            id = null,
            name = "Test",
            core = 1,
            attraction = 1,
            skill1Level = 1,
            skill2Level = 1,
            burstLevel = 1,
            rarity = Rarity.SSR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.PILGRIM,
            code = Code.ELECTRIC,
            weapon = Weapon.SR,
            nikkeClass = NikkeClass.ATTACKER,
            cube = null,
            doll = null
        )
        entityManager.persist(nikke)
        entityManager.flush()

        val name = "Test"

        nikkeRepository.deleteByName(name)

        val result = nikkeRepository.findNikkeByName(name)
        assertThat(result).isNull()
    }

    //Update
//    @Test
//    @DisplayName("Testar o update novo")
//    fun updateNikkeByNameSuccess(){
//
//        //Arrange
//        val nikke = Nikke(
//            id = null,
//            name = "Test",
//            core = 1,
//            attraction = 1,
//            skill1Level = 1,
//            skill2Level = 1,
//            burstLevel = 1,
//            rarity = Rarity.SSR,
//            ownedStatus = OwnedStatus.NOT_OWNED,
//            burstType = BurstType.III,
//            company = Company.PILGRIM,
//            code = Code.ELECTRIC,
//            weapon = Weapon.SR,
//            nikkeClass = NikkeClass.ATTACKER,
//            cube = null,
//            doll = null
//        )
//
//        val nikkeUpdated = Nikke(
//            id = null,
//            name = "Test",
//            core = 1,
//            attraction = 1,
//            skill1Level = 1,
//            skill2Level = 1,
//            burstLevel = 1,
//            rarity = Rarity.SSR,
//            ownedStatus = OwnedStatus.NOT_OWNED,
//            burstType = BurstType.III,
//            company = Company.ELYSION,
//            code = Code.ELECTRIC,
//            weapon = Weapon.SR,
//            nikkeClass = NikkeClass.ATTACKER,
//            cube = null,
//            doll = null
//        )
//
//        //Act
//        nikkeRepository.updateNikkeByName(
//            name = nikkeUpdated.name,
//            core = nikkeUpdated.core,
//            attraction = nikkeUpdated.attraction,
//            skill1Level = nikkeUpdated.skill1Level ,
//            skill2Level = nikkeUpdated.skill2Level,
//            burstLevel = nikkeUpdated.burstLevel,
//            rarity = nikkeUpdated.rarity,
//            ownedStatus = nikkeUpdated.ownedStatus,
//            burstType = nikkeUpdated.burstType,
//            company = nikkeUpdated.company,
//            code = nikkeUpdated.code,
//            weapon = nikkeUpdated.weapon,
//            nikkeClass = nikkeUpdated.nikkeClass,
//            cube = nikkeUpdated.cube,
//            doll = nikkeUpdated.doll
//        )
//
//        //Assert
//        val result = nikkeRepository.findByName(nikkeUpdated.name)
//        assertThat(result?.company).isEqualTo(nikkeUpdated.company)
//    }
}
