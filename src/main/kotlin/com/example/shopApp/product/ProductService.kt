package com.example.shopApp.product

import com.example.shopApp.product.exception.ProductNotFoundException
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Service

@Service
class ProductService(
        private val productRepository: ProductRepository,
        private val productCsvParser: ProductCsvParser
) {

    fun getProductById(productId: Long): Product {
        return productRepository.findById(productId)
                .orElseThrow { ProductNotFoundException("No product found with the following id: $productId") }
    }

    fun removeProductById(productId: Long) {
        return productRepository.deleteById(productId)
    }

    fun addNewProduct(productRequest: ProductRequest) {
        val newProduct = Product.fromProductRequest(productRequest)
        productRepository.save(newProduct)
    }

    fun getAllProducts(): List<Product> {
        return productRepository.findAll()
    }

    fun getAllProductDataInCsv(): ByteArrayResource {
        val allProducts = productRepository.findAll()
        return productCsvParser.writeToCsv(allProducts)
    }
}