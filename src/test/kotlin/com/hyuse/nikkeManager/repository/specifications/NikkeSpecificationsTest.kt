package com.hyuse.nikkeManager.repository.specifications

import com.hyuse.nikkeManager.enums.*
import com.hyuse.nikkeManager.model.Nikke
import com.hyuse.nikkeManager.repository.NikkeRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
class NikkeSpecificationsTest {

    @Autowired
    private lateinit var nikkeRepository: NikkeRepository

    @Autowired
    private lateinit var entityManager: TestEntityManager

    private lateinit var nikke1: Nikke
    private lateinit var nikke2: Nikke
    private lateinit var nikke3: Nikke

    @BeforeEach
    fun setup() {
        // Criar dados de teste
        nikke1 = Nikke(
            id = null,
            name = "Nikke1",
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

        nikke2 = Nikke(
            id = null,
            name = "Nikke2",
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

        nikke3 = Nikke(
            id = null,
            name = "Nikke3",
            core = 1,
            attraction = 1,
            skill1Level = 1,
            skill2Level = 1,
            burstLevel = 1,
            rarity = Rarity.SR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.PILGRIM,
            code = Code.ELECTRIC,
            weapon = Weapon.SR,
            nikkeClass = NikkeClass.ATTACKER,
            cube = null,
            doll = null
        )

        entityManager.persist(nikke1)
        entityManager.persist(nikke2)
        entityManager.persist(nikke3)
        entityManager.flush()
    }

    @Test
    @DisplayName("should filter by single criterion - rarity")
    fun filterSingleTest() {
        val result = nikkeRepository.findAll(
            NikkeSpecifications.byFilters(
                rarity = Rarity.SSR,
                ownedStatus = null,
                burstType = null,
                company = null,
                code = null,
                weapon = null,
                nikkeClass = null,
                cube = null
            )
        )

        assertThat(result).hasSize(2)
        assertThat(result).extracting("rarity")
            .containsOnly(Rarity.SSR)
    }
}