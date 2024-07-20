package com.example.shopApp.promocode

import com.example.shopApp.cache.CacheNames
import com.example.shopApp.promocode.model.PromoCode
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class PromoCodeLocalRepository(
    private val promoCodeMongoRepository: PromoCodeMongoRepository
) : PromoCodeRepository {

    @CachePut(cacheNames = [CacheNames.PROMO_CODE], key = "#promoCode.code")
    override fun addNewCode(promoCode: PromoCode): PromoCode {
        return promoCodeMongoRepository.save(promoCode)
    }

    @Cacheable(cacheNames = [CacheNames.PROMO_CODE])
    override fun findByCode(code: String): PromoCode? {
        return promoCodeMongoRepository.findByCode(code)
    }

    override fun existsByCode(code: String): Boolean {
        return findByCode(code) != null
    }

    @CacheEvict(cacheNames = [CacheNames.PROMO_CODE])
    override fun deleteByCode(code: String): Boolean {
        return promoCodeMongoRepository.deleteByCode(code)
    }
}
