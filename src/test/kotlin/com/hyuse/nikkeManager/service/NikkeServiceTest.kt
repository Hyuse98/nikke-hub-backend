package com.hyuse.nikkeManager.service

import com.hyuse.nikkeManager.dto.NikkeDTO
import com.hyuse.nikkeManager.enums.*
import com.hyuse.nikkeManager.model.Nikke
import com.hyuse.nikkeManager.repository.NikkeRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.isA
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class NikkeServiceTest() {

    @InjectMocks
    lateinit var nikkeService: NikkeService

    @Mock
    lateinit var nikkeRepository: NikkeRepository

    @BeforeEach
    fun setup() {

    }

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

        whenever(nikkeRepository.findByName("Test")).thenReturn(null)
        whenever(nikkeRepository.save(isA<Nikke>())).thenReturn(nikkeDTO.toModel())

        val result = nikkeService.createNikke(nikkeDTO)

        verify(nikkeRepository, times(1)).findByName("Test")
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

        whenever(nikkeRepository.findByName("Test")).thenReturn(nikke)

        val exception = assertThrows<IllegalStateException> {
            nikkeService.createNikke(nikkeDTO)
        }
        assertThat(exception.message).isEqualTo("Nikke Already Exist")

        verify(nikkeRepository, times(1)).findByName("Test")
        verify(nikkeRepository, times(0)).save(isA<Nikke>())
    }

    @Test
    @DisplayName("Should uptade a nikke")
    fun updateNikkeCase1(){

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

        whenever(nikkeRepository.findByName("Test")).thenReturn(nikke)
        whenever(nikkeRepository.save(isA<Nikke>())).thenReturn(nikkeDTO.toModel())

        val result: Nikke = nikkeService.updateNikke(nikkeDTO, nikke.name)

        assertThat(result).isNotNull
        assertThat(result.name).isEqualTo(nikkeDTO.name)

        verify(nikkeRepository, times(1)).findByName("Test")
        verify(nikkeRepository, times(1)).save(isA<Nikke>())
    }

}