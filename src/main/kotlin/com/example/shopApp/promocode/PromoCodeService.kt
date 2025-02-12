package com.example.shopApp.promocode

import com.example.shopApp.promocode.PromoCodeService.ValidationResult.*
import com.example.shopApp.promocode.exception.PromoCodeAlreadyExistsException
import com.example.shopApp.promocode.exception.PromoCodeNotFoundException
import com.example.shopApp.promocode.model.NewPromoCodeRequest
import com.example.shopApp.promocode.model.PromoCode
import com.example.shopApp.promocode.model.PromoCodeDto
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class PromoCodeService(private val promoCodeRepository: PromoCodeRepository) {

    fun addPromoCode(request: NewPromoCodeRequest) {
        val promoCode = PromoCode.fromNewPromoCodeRequest(request)
        if (promoCodeRepository.existsByCode(promoCode.code)) {
            throw PromoCodeAlreadyExistsException("Promo code already exists!")
        }
        promoCodeRepository.addNewCode(promoCode)
    }

    fun validate(code: String): ValidationResult {
        val promoCode = promoCodeRepository.findByCode(code) ?: return CodeNotFound()
        val now = LocalDate.now()
        if (now.isAfter(promoCode.endDate)) {
            return CodeHasExpired()
        }
        return ValidationPassed
    }

    fun findPromoCodeByCode(code: String): PromoCodeDto {
        val promoCode = promoCodeRepository.findByCode(code) ?: throw PromoCodeNotFoundException()
        return promoCode.toPromoCodeDto()
    }

    fun deletePromoCode(code: String) {
        promoCodeRepository.deleteByCode(code)
    }

    sealed interface ValidationResult {
        data object ValidationPassed : ValidationResult
        data class CodeNotFound(val message: String = "Promo code not found") : ValidationResult
        data class CodeHasExpired(val message: String = "Promo code has expired") : ValidationResult
    }
}
