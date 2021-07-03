package com.mikhailsycheuski.test.warehouse.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import javax.sql.DataSource

@Configuration
@ConditionalOnClass(DataSource::class)
@EnableJdbcRepositories(basePackages = ["com.mikhailsycheuski.test.warehouse"])
@EnableJdbcAuditing(
  modifyOnCreate = false,
  dateTimeProviderRef = "dateTimeProvider"
)
@EnableTransactionManagement
class DataSourceConfiguration {

  // Core configurations -----------------------------------------------------------------------------------------------

  @Bean
  fun dateTimeProvider(): DateTimeProvider =
    DateTimeProvider { Optional.of(LocalDateTime.now(ZoneOffset.UTC)) }

  @Bean
  @Primary
  fun jdbcTemplate(dataSource: DataSource): JdbcOperations = JdbcTemplate(dataSource)

  @Bean
  @Primary
  fun namedParameterJdbcTemplate(dataSource: DataSource): NamedParameterJdbcOperations =
    NamedParameterJdbcTemplate(dataSource)

  @Bean
  @Primary
  fun transactionManager(dataSource: DataSource): PlatformTransactionManager =
    DataSourceTransactionManager(dataSource)

  // App DataSource configuration --------------------------------------------------------------------------------------

  @Bean
  @ConfigurationProperties(prefix = "spring.app-data-source")
  fun appDataSourceProperties() = DataSourceProperties()

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.app-data-source.hikary")
  fun appDataSource(
    @Qualifier("appDataSourceProperties") dataSourceProperties: DataSourceProperties
  ): HikariDataSource =
    dataSourceProperties
      .initializeDataSourceBuilder()
      .type(HikariDataSource::class.java)
      .build()

  // Migration DataSource configuration --------------------------------------------------------------------------------

  @Bean
  @ConfigurationProperties(prefix = "spring.migration-data-source")
  fun migrationDataSourceProperties() = DataSourceProperties()

  @Bean
  @FlywayDataSource
  @ConfigurationProperties(prefix = "spring.migration-data-source.hikary")
  fun migrationDataSource(
    @Qualifier("migrationDataSourceProperties") dataSourceProperties: DataSourceProperties
  ): HikariDataSource {
    return dataSourceProperties
      .initializeDataSourceBuilder()
      .type(HikariDataSource::class.java)
      .build()
  }
}