package com.hyuse.nikkeManager.infrastructure.database.jpa.repository

import com.hyuse.nikkeManager.domain.entities.Nikke
import com.hyuse.nikkeManager.domain.enums.*
import com.hyuse.nikkeManager.domain.vo.AttractionLevel
import com.hyuse.nikkeManager.domain.vo.CharacterName
import com.hyuse.nikkeManager.domain.vo.CoreLevel
import com.hyuse.nikkeManager.domain.vo.SkillLevel
import com.hyuse.nikkeManager.infrastructure.database.jpa.repository.nikke.NikkeRepositoryImpl
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.exception.ConstraintViolationException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(properties = ["spring.cloud.config.enabled=false"])
@Import(NikkeRepositoryImpl::class)
class NikkeRepositoryImplTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var nikkeRepository: NikkeRepositoryImpl

    private fun createNikkeForTest(name: String): Nikke {
        return Nikke.create(
            name = CharacterName.of(name),
            core = CoreLevel.of(0),
            attraction = AttractionLevel.of(1),
            skill1 = SkillLevel.of(1),
            skill2 = SkillLevel.of(1),
            skillBurst = SkillLevel.of(1),
            rarity = Rarity.SSR,
            ownedStatus = OwnedStatus.NOT_OWNED,
            burstType = BurstType.III,
            company = Company.TETRA,
            code = Code.FIRE,
            weapon = Weapon.AR,
            nikkeClass = NikkeClass.ATTACKER
        )
    }

    @Nested
    inner class Creation() {

        @Test
        fun `should save a nikke and find it by id`() {

            val nikkeToSave = createNikkeForTest("Test Nikke")
            val savedNikke = nikkeRepository.save(nikkeToSave)
            assertThat(savedNikke.id).isNotNull()

            val foundNikke = nikkeRepository.findById(savedNikke.id!!)

            assertThat(foundNikke).isPresent
            assertThat(foundNikke.get().name.value).isEqualTo("Test Nikke")
        }

        @Test
        fun `should throw DataIntegrityViolationException when saving a nikke with a duplicate name`() {

            val nikke1 = createNikkeForTest("Rapi")
            nikkeRepository.save(nikke1)

            entityManager.flush()

            val nikkeWithDuplicateName = createNikkeForTest("Rapi")

            assertThrows<ConstraintViolationException> {

                nikkeRepository.save(nikkeWithDuplicateName)

                entityManager.flush()
            }
        }

    }

    @Nested
    inner class Get() {

        @Test
        fun `should not find a nikke by id`() {

            val name = "Neon"
            val nikke = nikkeRepository.findByName(name)

            assertThat(nikke.isEmpty)
        }

        @Test
        fun `should save a nikke and find it by name`() {

            val name = "Neon"
            val nikkeToSave = createNikkeForTest(name)
            nikkeRepository.save(nikkeToSave)

            val nikke = nikkeRepository.findByName(name)

            assertThat(nikke.isPresent)
            assertThat(nikke.get().name.value).isEqualTo(name)

        }

        @Test
        fun `should not find a nikke`() {

            val name = "Neon"

            val nikke = nikkeRepository.findByName(name)

            assertThat(nikke.isEmpty)
        }
    }

    @Nested
    inner class Update() {

        @Test
        fun `should update an existing nikke`() {

            val nikkeToSave = createNikkeForTest("Update Test")
            val savedNikke = nikkeRepository.save(nikkeToSave)
            val originalId = savedNikke.id!!

            val nikkeToUpdate = Nikke.reconstitute(
                id = originalId,
                name = CharacterName.of("Updated Nikke"),
                core = savedNikke.core,
                attraction = savedNikke.attraction,
                skill1 = savedNikke.skill1,
                skill2 = savedNikke.skill2,
                skillBurst = savedNikke.skillBurst,
                rarity = Rarity.SR,
                ownedStatus = OwnedStatus.OWNED,
                burstType = BurstType.I,
                company = Company.ELYSION,
                code = Code.WATER,
                weapon = Weapon.SG,
                nikkeClass = NikkeClass.DEFENDER,
                cube = Cubes.BASTION
            )

            val updatedNikke = nikkeRepository.update(nikkeToUpdate)

            assertThat(updatedNikke.name.value).isEqualTo("Updated Nikke")
            assertThat(updatedNikke.rarity).isEqualTo(Rarity.SR)

            val foundNikke = nikkeRepository.findById(originalId)

            assertThat(foundNikke).isPresent
            assertThat(foundNikke.get().name.value).isEqualTo("Updated Nikke")
        }
    }

    @Nested
    inner class Delete() {

        @Test
        fun `should delete a nikke by id`() {

            val nikkeToSave = createNikkeForTest("Delete Test")
            val savedNikke = nikkeRepository.save(nikkeToSave)
            val id = savedNikke.id!!

            assertThat(nikkeRepository.existsById(id)).isTrue()
            nikkeRepository.delete(id)
            assertThat(nikkeRepository.existsById(id)).isFalse()
        }
    }

    @Nested
    inner class List() {

        @Test
        fun `should return all nikkes`() {
            nikkeRepository.save(createNikkeForTest("Nikke 1"))
            nikkeRepository.save(createNikkeForTest("Nikke 2"))

            val allNikkes = nikkeRepository.findAll()

            assertThat(allNikkes).hasSize(2)
        }

        @Test
        fun `should throw a exception when list is null or empty`() {

            val allNikkes = nikkeRepository.findAll()

            assertThat(allNikkes).isEmpty()
        }
    }

    @Nested
    inner class Checks() {

        @Test
        fun `should check if nikke exists by name`() {
            nikkeRepository.save(createNikkeForTest("Existing Nikke"))

            assertThat(nikkeRepository.existsByName("Existing Nikke")).isTrue()
            assertThat(nikkeRepository.existsByName("Non-Existing Nikke")).isFalse()
        }
    }
}