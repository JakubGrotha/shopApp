package com.example.shopApp.integration

import com.example.shopApp.common.Builders
import com.example.shopApp.order.OrderRepository
import com.example.shopApp.product.ProductRepository
import com.example.shopApp.promocode.PromoCodeMongoRepository
import com.example.shopApp.user.AppUserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.cache.CacheManager
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.lifecycle.Startables

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(initializers = [AbstractIntegrationTest.Companion.Initializer::class])
@AutoConfigureMockMvc(addFilters = false)
abstract class AbstractIntegrationTest {

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @Autowired
    protected lateinit var promoCodeMongoRepository: PromoCodeMongoRepository

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Autowired
    protected lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var appUserRepository: AppUserRepository

    @Autowired
    private lateinit var addressRepository: AppUserRepository

    @Autowired
    private lateinit var cacheManager: CacheManager

    @BeforeEach
    fun setUp() {
        cleanDatabases()
        clearCache()
    }

    private fun cleanDatabases() {
        promoCodeMongoRepository.deleteAll()
        orderRepository.deleteAll()
        productRepository.deleteAll()
        appUserRepository.deleteAll()
        addressRepository.deleteAll()
    }

    private fun clearCache() {
        cacheManager.cacheNames
            .forEach { cacheManager.getCache(it)?.clear() }
    }

    companion object {

        @JvmStatic
        protected val builders = Builders()

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
