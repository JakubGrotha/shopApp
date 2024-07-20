package com.example.shopApp.cache

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
@EnableCaching
class CacheConfiguration {

    @Value("\${app.cache.spec}")
    private lateinit var cacheSpec: String

    @Bean
    fun promoCodeCache(): CaffeineCache {
        return CaffeineCache(CacheNames.PROMO_CODE, Caffeine.from(cacheSpec).build())
    }
}
