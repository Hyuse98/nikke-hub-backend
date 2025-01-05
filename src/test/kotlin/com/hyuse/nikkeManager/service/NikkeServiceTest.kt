package com.hyuse.nikkeManager.service

import com.hyuse.nikkeManager.dto.NikkeDTO
import com.hyuse.nikkeManager.enums.*
import com.hyuse.nikkeManager.model.Nikke
import com.hyuse.nikkeManager.repository.NikkeRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
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

    //@Autowired
    @InjectMocks
    lateinit var nikkeService: NikkeService

    //@Autowired
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

        whenever(nikkeRepository.findByName("Test")).thenReturn(null)
        whenever(nikkeRepository.save(isA<Nikke>())).thenReturn(nikkeDTO.toModel())

        val result = nikkeService.createNikke(nikkeDTO)

        verify(nikkeRepository, times(1)).findByName("Test")
        verify(nikkeRepository, times(1)).save(isA<Nikke>())

        assertThat(result).isNotNull
        assertThat(result.name).isEqualTo(nikkeDTO.name)

    }

}