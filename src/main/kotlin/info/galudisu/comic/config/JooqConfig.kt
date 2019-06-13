package info.galudisu.comic.config

import org.jooq.DSLContext
import org.jooq.ExecuteContext
import org.jooq.ExecuteListenerProvider
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.tools.JooqLogger
import org.jooq.tools.LoggerListener
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.jooq.JooqProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

/// only use to format sql, convenient debugging
@Configuration
@EnableConfigurationProperties(JooqProperties::class)
class JooqConfig {

    @Bean
    @ConditionalOnMissingBean(org.jooq.Configuration::class)
    fun dslContext(properties: JooqProperties, dataSource: DataSource): DSLContext =
            DSL.using(
                    DefaultConfiguration()
                            .set(DataSourceConnectionProvider(dataSource))
                            .set(properties.sqlDialect)
                            .set(Settings()
                                    .withRenderFormatted(true)
                                    .withExecuteLogging(false))
                            .set(ExecuteListenerProvider {
                                object : LoggerListener() {
                                    override fun renderEnd(ctx: ExecuteContext?) {
                                        super.renderEnd(ctx)
                                        val log = JooqLogger.getLogger(LoggerListener::class.java)
                                        if (log.isDebugEnabled) {
                                            if (ctx!!.routine() != null) {
                                                log.debug(DSL.using(ctx.configuration()).renderInlined(ctx.routine())
                                                )
                                            }
                                        }
                                    }
                                }
                            })
            )


}