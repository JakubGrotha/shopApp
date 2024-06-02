package com.example.shopApp.order

import com.example.shopApp.order.exception.OrderNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class OrderExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [OrderNotFoundException::class])
    fun handleException(exception: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        val responseBody = exception.message
        return handleExceptionInternal(exception, responseBody, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }
}