package com.example.shopApp.promocode

import com.example.shopApp.promocode.PromoCodeService.ValidationResult.*
import com.example.shopApp.promocode.model.NewPromoCodeRequest
import com.example.shopApp.promocode.model.PromoCodeDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/promo-code")
class PromoCodeController(
        private val promoCodeService: PromoCodeService
) {

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(value = ["ADMIN"])
    fun addPromoCode(@RequestBody request: NewPromoCodeRequest) {
        promoCodeService.addPromoCode(request)
    }

    @GetMapping("/{code}")
    @Secured(value = ["ADMIN"])
    fun getPromoCodeByCode(@PathVariable("code") code: String): PromoCodeDto {
        return promoCodeService.findPromoCodeByCode(code)
    }

    @GetMapping("/validate/{code}")
    fun validate(@PathVariable("code") code: String): ResponseEntity<Any> {
        return when (val validationResult = promoCodeService.validate(code)) {
            is ValidationPassed -> ResponseEntity.ok().build()
            is CodeNotFound -> ResponseEntity.badRequest().body(validationResult.message)
            is CodeHasExpired -> ResponseEntity.badRequest().body(validationResult.message)
        }
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured(value = ["ADMIN"])
    fun deleteById(@PathVariable("code") code: String) {
        promoCodeService.deletePromoCode(code)
    }
}
