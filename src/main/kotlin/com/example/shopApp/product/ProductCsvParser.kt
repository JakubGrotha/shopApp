package com.example.shopApp.product

import com.opencsv.CSVWriter
import com.opencsv.CSVWriterBuilder
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter

@Component
class ProductCsvParser {

    fun writeToCsv(allProducts: List<Product>): ByteArrayResource {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val csvWriter = CSVWriterBuilder(OutputStreamWriter(byteArrayOutputStream)).run {
            withSeparator(';')
            withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
            withLineEnd(CSVWriter.DEFAULT_LINE_END)
            this.build()
        }
        csvWriter.use { writer ->
            val header = arrayOf("Name", "Price", "Quantity")
            writer.writeNext(header)
            allProducts.forEach { product ->
                val data = arrayOf(product.name, product.price.toString(), product.quantity.toString())
                writer.writeNext(data)
            }
        }
        return ByteArrayResource(byteArrayOutputStream.toByteArray())
    }
}