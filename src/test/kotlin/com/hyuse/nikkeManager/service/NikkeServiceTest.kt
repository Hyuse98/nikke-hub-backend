package com.hyuse.nikkeManager.service

import com.hyuse.nikkeManager.dto.NikkeDTO
import com.hyuse.nikkeManager.enums.*
import com.hyuse.nikkeManager.exception.NikkeAlreadyExistsException
import com.hyuse.nikkeManager.exception.NikkeIdNotFoundException
import com.hyuse.nikkeManager.exception.NikkeNotFoundException
import com.hyuse.nikkeManager.model.Nikke
import com.hyuse.nikkeManager.repository.NikkeRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.domain.Specification
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertEquals

@SpringBootTest
@ActiveProfiles("test")
class NikkeServiceTest {

    @InjectMocks
    lateinit var nikkeService: NikkeService

    @Mock
    lateinit var nikkeRepository: NikkeRepository

    @Test
    @DisplayName("Should create a nikke with success")
    fun createNikkeCase1() {

        val nikkeDTO = NikkeDTO(
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

        whenever(nikkeRepository.findNikkeByName("Test")).thenReturn(null)
        whenever(nikkeRepository.save(isA<Nikke>())).thenReturn(nikkeDTO.toModel())

        val result = nikkeService.createNikke(nikkeDTO)

        verify(nikkeRepository, times(1)).findNikkeByName("Test")
        verify(nikkeRepository, times(1)).save(isA<Nikke>())

        assertThat(result).isNotNull
        assertThat(result.name).isEqualTo(nikkeDTO.name)

    }

    @Test
    @DisplayName("Should not create a nikke due to a nikke already existing")
    fun createNikkeCase2() {

        val nikkeDTO = NikkeDTO(
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

        whenever(nikkeRepository.findNikkeByName("Test")).thenReturn(nikke)

        val exception = assertThrows<NikkeAlreadyExistsException> {
            nikkeService.createNikke(nikkeDTO)
        }
        assertThat(exception.message).isEqualTo("Nikke with name '${nikke.name}' already exists")

        verify(nikkeRepository, times(1)).findNikkeByName("Test")
        verify(nikkeRepository, times(0)).save(isA<Nikke>())
    }

    @Test
    @DisplayName("Should update a nikke")
    fun updateNikkeCase1() {

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

        val nikkeDTO = NikkeDTO(
            id = null,
            name = "Test2",
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

        whenever(nikkeRepository.findNikkeByName("Test")).thenReturn(nikke)
        whenever(nikkeRepository.save(isA<Nikke>())).thenReturn(nikkeDTO.toModel())

        val result: Nikke = nikkeService.updateNikke(nikkeDTO, nikke.name)

        assertThat(result).isNotNull
        assertThat(result.name).isEqualTo(nikkeDTO.name)

        verify(nikkeRepository, times(1)).findNikkeByName("Test")
        verify(nikkeRepository, times(1)).save(isA<Nikke>())
    }

    @Test
    @DisplayName("Should not update a nikke due to lack of a nikke with the same name passed as parameter")
    fun updateNikkeCase2() {

        val nikkeDTO = NikkeDTO(
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

        val name = "Test"

        whenever(nikkeRepository.findNikkeByName(name)).thenReturn(null)

        val exception = assertThrows<NikkeNotFoundException> {
            nikkeService.updateNikke(nikkeDTO, name)
        }
        assertThat(exception.message).isEqualTo("Nikke with name '${name}' not found")

        verify(nikkeRepository, times(1)).findNikkeByName("Test")
        verify(nikkeRepository, times(0)).save(isA<Nikke>())
    }

    @Test
    @DisplayName("Should update a nikke by id")
    fun updateNikkeIdCase1() {

        val nikke = Nikke(
            id = 1,
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

        val nikkeDTO = NikkeDTO(
            id = null,
            name = "Test2",
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

        whenever(nikkeRepository.findNikkeById(1)).thenReturn(nikke)
        whenever(nikkeRepository.save(isA<Nikke>())).thenReturn(nikkeDTO.toModel())

        val result: Nikke = nikkeService.updateNikke(nikkeDTO, nikke.id!!)

        assertThat(result).isNotNull
        assertThat(result.name).isEqualTo(nikkeDTO.name)

        verify(nikkeRepository, times(1)).findNikkeById(1)
        verify(nikkeRepository, times(1)).save(isA<Nikke>())
    }

    @Test
    @DisplayName("Should not update a nikke by id")
    fun updateNikkeIdCase2() {

        val nikkeDTO = NikkeDTO(
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

        val id = 1

        whenever(nikkeRepository.findNikkeById(id)).thenReturn(null)
        whenever(nikkeRepository.save(isA<Nikke>())).thenReturn(nikkeDTO.toModel())

        val exception = assertThrows<NikkeIdNotFoundException> {
            nikkeService.updateNikke(nikkeDTO, id)
        }
        assertThat(exception.message).isEqualTo("Nikke with id '$id' not found")

        verify(nikkeRepository, times(1)).findNikkeById(id)
    }

    @Test
    @DisplayName("Should delete nikke by name passed")
    fun deleteNikkeByNameCase1() {

        val nikke = Nikke(
            id = 1,
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

        whenever(nikkeRepository.findNikkeByName("Test")).thenReturn(nikke)

        nikkeService.deleteNikke("Test")

        verify(nikkeRepository, times(1)).deleteByName("Test")
    }

    @Test
    @DisplayName("Should fail when delete nikke because not exist")
    fun deleteNikkeByNameCase2() {

        val name = "Test"
        val exception = assertThrows<NikkeNotFoundException> {
            nikkeService.deleteNikke(name)
        }
        assertThat(exception.message).isEqualTo("Nikke with name '${name}' not found")

        verify(nikkeRepository, times(1)).findNikkeByName(name)
    }

    @Test
    @DisplayName("Should delete nikke by id passed")
    fun deleteNikkeByIdCase1() {

        val nikke = Nikke(
            id = 1,
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

        whenever(nikkeRepository.findNikkeById(1)).thenReturn(nikke)

        nikkeService.deleteNikke(1)

        verify(nikkeRepository, times(1)).deleteById(1)
    }

    @Test
    @DisplayName("Should fail when delete nikke by id because not exist")
    fun deleteNikkeByIdCase2() {

        val id = 1

        val exception = assertThrows<NikkeIdNotFoundException> {
            nikkeService.deleteNikke(id)
        }
        assertThat(exception.message).isEqualTo("Nikke with id '$id' not found")

        verify(nikkeRepository, times(1)).findNikkeById(id)
    }

    @Test
    @DisplayName("should list all nikkes when no filters are provided")
    fun listNikkeCase1() {

        val nikke1 = Nikke(
            id = 1,
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

        val nikke2 = Nikke(
            id = 2,
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

        val nikke3 = Nikke(
            id = 3,
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

        val expectedNikkes = listOf(
            nikke1, nikke2, nikke3
        )

        whenever(nikkeRepository.findAll(any<Specification<Nikke>>())).thenReturn(expectedNikkes)

        val result = nikkeService.listAllNikke(
            rarity = null,
            ownedStatus = null,
            burstType = null,
            company = null,
            code = null,
            weapon = null,
            nikkeClass = null,
            cube = null
        )

        verify(nikkeRepository).findAll(any<Specification<Nikke>>())
        assertEquals(expectedNikkes, result)
    }

    @Test
    @DisplayName("should find a nikke by name passed")
    fun searchNikkeCase1() {

        val nikke = Nikke(
            id = 1,
            name = "Rapi",
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

        val name = "Rapi"

        whenever(nikkeRepository.findNikkeByName(name)).thenReturn(nikke)

        val result = nikkeService.searchNikke(name)

        assertThat(result).isNotNull
        assertThat(result!!.name).isEqualTo(name)

        verify(nikkeRepository, times(1)).findNikkeByName(name)
    }

    @Test
    @DisplayName("should not find a nikke by name passed")
    fun searchNikkeCase2() {

        val name = "Rapi"

        whenever(nikkeRepository.findNikkeByName(name)).thenReturn(null)

        val result = nikkeService.searchNikke(name)

        assertThat(result).isNull()

        verify(nikkeRepository, times(1)).findNikkeByName(name)
    }
}