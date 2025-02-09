package com.hyuse.nikkeManager

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@OpenAPIDefinition(info = Info(title = "Nikke Manager", version = "Beta", description = "API to manage Nikkes"))
@SpringBootApplication
@EnableDiscoveryClient
class NikkeManagerApplication

fun main(args: Array<String>) {
	runApplication<NikkeManagerApplication>(*args)
}