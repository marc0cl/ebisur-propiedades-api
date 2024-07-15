package org.ebisur.config.db

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["org.ebisur.repository"],
    entityManagerFactoryRef = "userEntityManager",
    transactionManagerRef = "userTransactionManager"
)
class UserDbConfig {

    @Primary
    @Bean(name = ["userDataSource"])
    @ConfigurationProperties(prefix = "users.datasource")
    fun dataSource(): DataSource = DataSourceBuilder.create().build()

    @Primary
    @Bean(name = ["userEntityManager"])
    fun entityManager(
        @Qualifier("userDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource
        em.setPackagesToScan("org.ebisur.model")
        val vendorAdapter = HibernateJpaVendorAdapter()
        em.jpaVendorAdapter = vendorAdapter
        val properties = HashMap<String, Any>()
        properties["hibernate.hbm2ddl.auto"] = "update"
        properties["hibernate.dialect"] = "org.hibernate.dialect.MySQL8Dialect"
        em.setJpaPropertyMap(properties)
        return em
    }

    @Primary
    @Bean(name = ["userTransactionManager"])
    fun transactionManager(
        @Qualifier("userEntityManager") entityManager: LocalContainerEntityManagerFactoryBean
    ): PlatformTransactionManager = JpaTransactionManager(entityManager.`object`!!)
}