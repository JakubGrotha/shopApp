package com.example.shopApp.promocode

import com.example.shopApp.promocode.model.PromoCode

interface PromoCodeRepository {

    fun addNewCode(promoCode: PromoCode): PromoCode
    fun findByCode(code: String): PromoCode?
    fun existsByCode(code: String): Boolean
    fun deleteByCode(code: String): Boolean
}