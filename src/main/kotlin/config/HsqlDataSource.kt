package config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
@PropertySource("classpath:/application.properties")
open class HsqlDataSource {
    @Bean
    open fun dataSource(environment: Environment): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver")
        dataSource.url = environment.getProperty("hsql.url")

        return dataSource
    }
}