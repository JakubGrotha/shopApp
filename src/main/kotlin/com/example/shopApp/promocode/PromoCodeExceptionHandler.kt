package com.example.shopApp.promocode

import com.example.shopApp.promocode.exception.PromoCodeAlreadyExistsException
import com.example.shopApp.promocode.exception.PromoCodeNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class PromoCodeExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [PromoCodeAlreadyExistsException::class, PromoCodeNotFoundException::class])
    fun handlePromoCodeAlreadyExistsException(exception: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        val responseBody = exception.message
        val httpStatus = getStatusCode(exception)
        return handleExceptionInternal(exception, responseBody, HttpHeaders(), httpStatus, request)
    }

    private fun getStatusCode(exception: RuntimeException) = when (exception) {
        is PromoCodeAlreadyExistsException -> HttpStatus.BAD_REQUEST
        is PromoCodeNotFoundException -> HttpStatus.NOT_FOUND
        else -> HttpStatus.INTERNAL_SERVER_ERROR
    }
}
