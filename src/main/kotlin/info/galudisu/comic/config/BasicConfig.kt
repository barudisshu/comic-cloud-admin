package info.galudisu.comic.config

import info.galudisu.comic.system.security.JwtConfig
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(JwtConfig::class)
class BasicConfig {
}