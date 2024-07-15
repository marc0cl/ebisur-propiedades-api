package org.ebisur.config.db

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["org.ebisur.repository"],
    includeFilters = [org.springframework.context.annotation.ComponentScan.Filter(
        type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE,
        classes = [org.ebisur.repository.BookingRepository::class]
    )],
    entityManagerFactoryRef = "bookingEntityManager",
    transactionManagerRef = "bookingTransactionManager"
)
class BookingDbConfig {

    @Bean(name = ["bookingDataSource"])
    @ConfigurationProperties(prefix = "bookings.datasource")
    fun dataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean(name = ["bookingEntityManager"])
    fun entityManager(
        @Qualifier("bookingDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean =
        LocalContainerEntityManagerFactoryBean().apply {
            this.dataSource = dataSource
            setPackagesToScan("org.ebisur.model")
            jpaVendorAdapter = HibernateJpaVendorAdapter()
        }

    @Bean(name = ["bookingTransactionManager"])
    fun transactionManager(
        @Qualifier("bookingEntityManager") entityManager: LocalContainerEntityManagerFactoryBean
    ): PlatformTransactionManager = JpaTransactionManager(entityManager.`object`!!)
}