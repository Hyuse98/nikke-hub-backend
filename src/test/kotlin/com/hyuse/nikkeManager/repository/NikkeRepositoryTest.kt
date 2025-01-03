package com.hyuse.nikkeManager.repository

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
class NikkeRepositoryTest {

    @Test
    @DisplayName("penis")
    fun findByName(){

    }
}