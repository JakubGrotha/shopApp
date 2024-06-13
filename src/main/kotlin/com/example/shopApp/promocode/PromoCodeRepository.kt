package com.example.shopApp.promocode

import com.example.shopApp.promocode.model.PromoCode
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PromoCodeRepository: MongoRepository<PromoCode, String> {

    fun findByCode(code: String): PromoCode?
    fun existsByCode(code: String): Boolean
}