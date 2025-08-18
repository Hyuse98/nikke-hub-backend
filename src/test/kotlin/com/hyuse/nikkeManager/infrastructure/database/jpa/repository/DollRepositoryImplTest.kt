package com.hyuse.nikkeManager.infrastructure.database.jpa.repository

import com.hyuse.nikkeManager.domain.entities.Doll
import com.hyuse.nikkeManager.domain.enums.*
import com.hyuse.nikkeManager.domain.ports.DollRepository
import com.hyuse.nikkeManager.domain.vo.DollLevel
import com.hyuse.nikkeManager.infrastructure.database.jpa.repository.doll.DollRepositoryImpl
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
@Import(DollRepositoryImpl::class)
class DollRepositoryImplTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var dollRepository: DollRepository

    private fun createDollForTest(rarity: Rarity, level: Int): Doll {
        return Doll.create(
            rarity = rarity,
            level = DollLevel.of(level)
        )
    }


    @Nested
    inner class Creation() {

        @Test
        fun `Should create a doll`() {

            val doll = createDollForTest(Rarity.SR, 1)
            val dollSaved = dollRepository.save(doll)
            val dollId = dollSaved.id!!

            assertThat(dollSaved.id).isNotNull()

            val dollEntity = dollRepository.findById(dollId)

            assertThat(dollEntity).isPresent
            assertThat(dollEntity.get().id).isEqualTo(dollId)
        }

//        @Test
//        fun `Should throw a exception when save duplicated doll`() {
//
//            val doll = createDollForTest(Rarity.SSR, 3)
//            dollRepository.save(doll)
//
//            entityManager.flush()
//
//            val dollWithDuplicateName = createDollForTest(Rarity.SSR, 3)
//
//            assertThrows<ConstraintViolationException> {
//
//                dollRepository.save(dollWithDuplicateName)
//
//                entityManager.flush()
//            }
//        }
    }

    @Nested
    inner class Get() {

        @Test
        fun `should not find a doll by id`() {

            val nikke = dollRepository.findById(1)
            assertThat(nikke.isEmpty)
        }

        @Test
        fun `should save a doll and find it by id`() {

            val doll = createDollForTest(Rarity.SR, 1)
            val dollSaved = dollRepository.save(doll)
            val dollId = dollSaved.id!!

            val dollEntity = dollRepository.findById(dollId)

            assertThat(dollEntity.isPresent)
            assertThat(dollEntity.get().id).isEqualTo(dollId)

        }

    }

    @Nested
    inner class Update() {

        @Test
        fun `should update an existing nikke`() {

            val doll = createDollForTest(Rarity.SR, 1)
            val dollSaved = dollRepository.save(doll)
            val dollId = dollSaved.id!!

            entityManager.flush()
            entityManager.clear()

            val dollToUpdate = Doll.reconstitute(
                id = dollId,
                rarity = Rarity.SSR,
                level = DollLevel.of(15)
            )

            val dollUpdated = dollRepository.update(
                id = dollId,
                doll = dollToUpdate
            )

            assertThat(dollUpdated.id).isEqualTo(dollId)
            assertThat(dollUpdated.rarity).isEqualTo(Rarity.SSR)
            assertThat(dollUpdated.level.value).isEqualTo(15)

            val dollEntity = dollRepository.findById(dollId)
            assertThat(dollEntity).isPresent
            assertThat(dollEntity.get().rarity).isEqualTo(Rarity.SSR)
            assertThat(dollEntity.get().level.value).isEqualTo(15)
        }

    }

    @Nested
    inner class Delete() {

        @Test
        fun `should delete a nikke by id`() {

            val nikkeToSave = createDollForTest(Rarity.SR, 1)
            val savedNikke = dollRepository.save(nikkeToSave)
            val id = savedNikke.id!!

            assertThat(dollRepository.existById(id)).isTrue()
            dollRepository.delete(id)
            assertThat(dollRepository.existById(id)).isFalse()
        }

    }

    @Nested
    inner class List() {

        @Test
        fun `should 1`() {
            dollRepository.save(createDollForTest(Rarity.SR, 1))
            dollRepository.save(createDollForTest(Rarity.R, 5))

            val allDolls = dollRepository.findAll()

            assertThat(allDolls).hasSize(2)
        }

        @Test
        fun `should throw 1`() {

            val allDolls = dollRepository.findAll()

            assertThat(allDolls).isEmpty()
        }

    }

    @Nested
    inner class Checks() {

        @Test
        fun `Should check if doll exist or not exist`() {

            dollRepository.save(createDollForTest(Rarity.SR, 3))

            assertThat(dollRepository.existById(1)).isTrue()
            assertThat(dollRepository.existById(2)).isFalse()
        }
    }
}