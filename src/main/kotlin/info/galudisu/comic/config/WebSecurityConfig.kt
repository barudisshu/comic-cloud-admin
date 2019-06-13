package info.galudisu.comic.config

import info.galudisu.comic.system.security.SecurityAuthenticationToken
import info.galudisu.comic.system.security.SecurityConstants
import info.galudisu.comic.system.security.SecurityRealm
import info.galudisu.comic.system.user.UserService
import org.apache.shiro.authc.credential.CredentialsMatcher
import org.apache.shiro.authc.credential.HashedCredentialsMatcher
import org.apache.shiro.realm.Realm
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebSecurityConfig {

    @Bean(name = ["realm"])
    fun realm(userService: UserService): Realm {
        val securityRealm = SecurityRealm(userService)
        securityRealm.credentialsMatcher = credentialsMatcher()
        securityRealm.setAuthenticationTokenClass(SecurityAuthenticationToken::class.java)
        return securityRealm
    }

    @Bean
    fun credentialsMatcher(): CredentialsMatcher {
        val credentialsMatcher = HashedCredentialsMatcher(SecurityConstants.HASH_ALGORITHM)
        credentialsMatcher.hashIterations = SecurityConstants.HASH_INTERATIONS
        credentialsMatcher.isStoredCredentialsHexEncoded = true
        return credentialsMatcher
    }
}