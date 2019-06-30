package info.galudisu.comic.system.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("jwt")
open class JwtConfig (
    internal val url: String = "/login",
    internal val header: String = "Authorization",
    internal val prefix: String = "Bearer",
    internal val expiration: Long = 24 * 60 * 60,
    internal val secret: String = "otherpeopledontknow"
)