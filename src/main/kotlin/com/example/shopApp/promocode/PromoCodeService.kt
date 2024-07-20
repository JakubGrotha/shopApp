package com.example.shopApp.promocode

import com.example.shopApp.promocode.exception.PromoCodeAlreadyExistsException
import com.example.shopApp.promocode.model.NewPromoCodeRequest
import com.example.shopApp.promocode.model.PromoCode
import com.example.shopApp.promocode.model.PromoCodeDto
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class PromoCodeService(
        private val promoCodeRepository: PromoCodeRepository
) {

    fun addPromoCode(request: NewPromoCodeRequest) {
        val promoCode = PromoCode.fromNewPromoCodeRequest(request)
        if (promoCodeRepository.existsByCode(promoCode.code)) {
            throw PromoCodeAlreadyExistsException("Promo code already exists!")
        }
        promoCodeRepository.insert(promoCode)
    }

    fun validate(code: String): ValidationResult {
        val promoCode = promoCodeRepository.findByCode(code)
        val endDate = promoCode?.endDate ?: return ValidationResult.CodeNotFound()
        val now = LocalDate.now()
        if (now.isBefore(endDate)) {
            return ValidationResult.ValidationPassed
        }
        return ValidationResult.CodeHasExpired()
    }

    fun findPromoCodeByCode(code: String): PromoCodeDto {
        val promoCode = promoCodeRepository.findByCode(code) ?: throw RuntimeException("No promo code found!")
        return promoCode.toPromoCodeDto()
    }

    fun deletePromoCode(promoCodeId: String) {
        promoCodeRepository.deleteById(promoCodeId)
    }

    sealed interface ValidationResult {
        data object ValidationPassed : ValidationResult
        class CodeNotFound(val message: String = "Promo code not found") : ValidationResult
        class CodeHasExpired(val message: String = "Promo code has expired") : ValidationResult
    }
}