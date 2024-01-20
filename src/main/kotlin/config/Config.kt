package config

import jakarta.persistence.EntityManagerFactory
import org.hibernate.jpa.HibernatePersistenceProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import java.util.*
import javax.sql.DataSource

@EnableWebMvc
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = ["app", "dao", "config", "validation"])
@PropertySource("classpath:/application.properties")
open class Config {
    @Bean
    open fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }

    @Bean
    open fun entityManagerFactory(dataSource: DataSource): EntityManagerFactory? {
        val populator = ResourceDatabasePopulator(ClassPathResource("schema.sql"))
        DatabasePopulatorUtils.execute(populator, dataSource)

        val factory = LocalContainerEntityManagerFactoryBean()
        factory.setPersistenceProviderClass(HibernatePersistenceProvider::class.java)
        factory.setPackagesToScan("model")
        factory.setDataSource(dataSource)
        factory.setJpaProperties(additionalProperties())
        factory.afterPropertiesSet()

        return factory.getObject()
    }

    private fun additionalProperties(): Properties {
        val properties = Properties()
        properties.setProperty("hibernate.hbm2ddl.auto", "validate")
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect")
        properties.setProperty("hibernate.show_sql", "false")
        properties.setProperty("hibernate.format_sql", "true")

        return properties
    }
}