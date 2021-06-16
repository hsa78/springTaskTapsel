package com.example.springTask

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringTaskApplication

fun main(args: Array<String>) {
	runApplication<SpringTaskApplication>(*args)
}
