package com.example.shopApp.product

import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController(
    private val productService: ProductService,
) {

    @GetMapping("/{productId}")
    fun getProductById(@PathVariable("productId") productId: Long): Product {
        return productService.getProductById(productId)
    }

    @GetMapping
    fun getAllProducts(): List<Product> {
        return productService.getAllProducts()
    }

    @DeleteMapping
    @Secured(value = ["ADMIN"])
    fun removeProductById(productId: Long) {
        productService.removeProductById(productId)
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Secured(value = ["ADMIN"])
    fun addNewProduct(@RequestBody newProduct: ProductRequest) {
        productService.addNewProduct(newProduct)
    }

    @GetMapping("/csv")
    fun exportAllProductDataInCsv(): ResponseEntity<ByteArrayResource> {
        val productsCsv = productService.getAllProductDataInCsv()
        val headers = HttpHeaders().apply {
            contentType = MediaType.parseMediaType("text/csv")
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products.csv")
        }
        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(productsCsv.contentLength())
            .body(productsCsv)
    }
}