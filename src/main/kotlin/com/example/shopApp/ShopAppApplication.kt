package com.example.shopApp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ShopAppApplication

fun main(args: Array<String>) {
    runApplication<ShopAppApplication>(*args)
}
