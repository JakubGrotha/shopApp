package com.example.shopApp.promocode

import com.example.shopApp.promocode.model.PromoCode
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PromoCodeMongoRepository : MongoRepository<PromoCode, String> {

    fun findByCode(code: String): PromoCode?
    fun deleteByCode(code: String): Boolean
}