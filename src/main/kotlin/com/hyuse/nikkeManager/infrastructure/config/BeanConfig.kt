package com.hyuse.nikkeManager.infrastructure.config

import com.hyuse.nikkeManager.domain.ports.DollRepository
import com.hyuse.nikkeManager.domain.ports.NikkeRepository
import com.hyuse.nikkeManager.domain.usecases.doll.*
import com.hyuse.nikkeManager.domain.usecases.nikke.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {

    @Bean
    fun createDollCase(dollRepository: DollRepository) = CreateDollCase(dollRepository)

    @Bean
    fun updateDollCase(dollRepository: DollRepository) = UpdateDollCase(dollRepository)

    @Bean
    fun deleteDollCase(dollRepository: DollRepository) = DeleteDollCase(dollRepository)

    @Bean
    fun getDollByIdCase(dollRepository: DollRepository) = GetDollByIdCase(dollRepository)

    @Bean
    fun getAllDollsCase(dollRepository: DollRepository) = GetAllDollsCase(dollRepository)

    @Bean
    fun changeNikkeOwnershipStatusCase(nikkeRepository: NikkeRepository) = ChangeNikkeOwnershipStatusCase(nikkeRepository)

    @Bean
    fun createNikkeCase(nikkeRepository: NikkeRepository) = CreateNikkeCase(nikkeRepository)

    @Bean
    fun correctNikkeBaseDataCase(nikkeRepository: NikkeRepository) = CorrectNikkeBaseDataCase(nikkeRepository)

    @Bean
    fun deleteNikkeCase(nikkeRepository: NikkeRepository) = DeleteNikkeCase(nikkeRepository)

    @Bean
    fun equipNikkeCubeCase(nikkeRepository: NikkeRepository) = EquipNikkeCubeCase(nikkeRepository)

    @Bean
    fun getNikkeByNameCase(nikkeRepository: NikkeRepository) = GetNikkeByNameCase(nikkeRepository)

    @Bean
    fun getAllNikkesCase(nikkeRepository: NikkeRepository) = GetAllNikkesCase(nikkeRepository)

    @Bean
    fun increaseNikkeAttractionCase(nikkeRepository: NikkeRepository) = IncreaseNikkeAttractionCase(nikkeRepository)

    @Bean
    fun levelUpNikkeBurstSkillCase(nikkeRepository: NikkeRepository) = LevelUpNikkeBurstSkillCase(nikkeRepository)

    @Bean
    fun levelUpNikkeSkill1Case(nikkeRepository: NikkeRepository) = LevelUpNikkeSkill1Case(nikkeRepository)

    @Bean
    fun levelUpNikkeSkill2Case(nikkeRepository: NikkeRepository) = LevelUpNikkeSkill2Case(nikkeRepository)

    @Bean
    fun upgradeNikkeCoreCase(nikkeRepository: NikkeRepository) = UpgradeNikkeCoreCase(nikkeRepository)
}