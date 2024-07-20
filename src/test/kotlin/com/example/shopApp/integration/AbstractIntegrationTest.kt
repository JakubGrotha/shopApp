package com.example.shopApp.integration

import com.example.shopApp.product.ProductRepository
import com.example.shopApp.promocode.PromoCodeRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.lifecycle.Startables

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = [AbstractIntegrationTest.Companion.Initializer::class])
@AutoConfigureMockMvc(addFilters = false)
abstract class AbstractIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var promoCodeRepository: PromoCodeRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    @BeforeEach
    fun setUp() {
        cleanDatabases()
    }

    private fun cleanDatabases() {
        promoCodeRepository.deleteAll()
        productRepository.deleteAll()
    }

    companion object {

        @Container
        private val postgres = PostgreSQLContainer<Nothing>("postgres:15")
            .apply {
                withDatabaseName("test")
                withUsername("test")
                withPassword("test")
            }

        val mongo = MongoDBContainer("mongo:7.0")
            .apply { withExposedPorts(27017) }

        internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

            init {
                Startables.deepStart(postgres).join()
                Startables.deepStart(mongo).join()
            }

            override fun initialize(applicationContext: ConfigurableApplicationContext) {
                TestPropertyValues.of(
                    "spring.datasource.url=${postgres.jdbcUrl}",
                    "spring.datasource.username=${postgres.username}",
                    "spring.datasource.password=${postgres.password}",
                    "spring.data.mongodb.uri=mongodb://${mongo.host}:${mongo.getMappedPort(27017)}"
                ).applyTo(applicationContext.environment)
            }
        }
    }
}
