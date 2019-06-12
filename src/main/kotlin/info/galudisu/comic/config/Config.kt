package info.galudisu.comic.config

import info.galudisu.comic.Constants
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.TransactionManagementConfigurer
import javax.sql.DataSource


@Configuration
class Config : TransactionManagementConfigurer {

    @Autowired
    internal var dataSource: DataSource? = null

    @Bean
    fun messageSource(): MessageSource {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setBasename("messages/messages")
        messageSource.setDefaultEncoding(Constants.ENCODING)
        return messageSource
    }

    override fun annotationDrivenTransactionManager(): PlatformTransactionManager {
        return transactionManager()
    }

    @Bean
    fun transactionManager(): PlatformTransactionManager {
        val txManager = DataSourceTransactionManager()
        txManager.dataSource = dataSource
        return txManager
    }

    companion object {
        private val logger = LoggerFactory.getLogger(Config::class.java)
    }
}