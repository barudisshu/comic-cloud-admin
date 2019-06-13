package info.galudisu.comic.config

import info.galudisu.comic.system.filter.StatelessAccessControlFilter
import info.galudisu.comic.system.security.SecurityConstants
import info.galudisu.comic.system.security.SecurityRealm
import info.galudisu.comic.system.security.StatelessAuthenticationToken
import info.galudisu.comic.system.security.StatelessDefaultSubjectFactory
import info.galudisu.comic.system.user.UserService
import org.apache.shiro.authc.credential.CredentialsMatcher
import org.apache.shiro.authc.credential.HashedCredentialsMatcher
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator
import org.apache.shiro.mgt.DefaultSubjectDAO
import org.apache.shiro.mgt.SecurityManager
import org.apache.shiro.realm.Realm
import org.apache.shiro.session.mgt.DefaultSessionManager
import org.apache.shiro.session.mgt.SessionManager
import org.apache.shiro.spring.LifecycleBeanPostProcessor
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager
import org.apache.shiro.web.session.mgt.WebSessionManager
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate
import java.util.*
import javax.servlet.Filter


@Configuration
class WebSecurityConfig {

    @Bean(name = ["realm"])
    fun realm(userService: UserService, redisTemplate: RedisTemplate<*,*>): Realm {
        val securityRealm = SecurityRealm(userService, redisTemplate)
        securityRealm.credentialsMatcher = credentialsMatcher()
        securityRealm.setAuthenticationTokenClass(StatelessAuthenticationToken::class.java)
        return securityRealm
    }

    @Bean("securityManager")
    fun securityManager(realm: Realm, sessionManager: WebSessionManager, subjectFactory: DefaultWebSubjectFactory): SecurityManager {
        val securityManager = DefaultWebSecurityManager()
        securityManager.subjectFactory = subjectFactory
        securityManager.setRealm(realm)
        securityManager.sessionManager = sessionManager
        securityManager.rememberMeManager = null

        ((securityManager.subjectDAO as DefaultSubjectDAO).sessionStorageEvaluator as DefaultSessionStorageEvaluator).isSessionStorageEnabled = false

        return securityManager
    }

    @Bean
    fun subjectFactory(): DefaultWebSubjectFactory {
        return StatelessDefaultSubjectFactory()
    }

    @Bean
    fun sessionManager(): WebSessionManager {
        val sessionManager = DefaultWebSessionManager()
        sessionManager.isSessionValidationSchedulerEnabled = false
        return sessionManager
    }
    @Bean
    fun credentialsMatcher(): CredentialsMatcher {
        val credentialsMatcher = HashedCredentialsMatcher(SecurityConstants.HASH_ALGORITHM)
        credentialsMatcher.hashIterations = SecurityConstants.HASH_INTERATIONS
        credentialsMatcher.isStoredCredentialsHexEncoded = true
        return credentialsMatcher
    }

    @Bean
    fun statelessAuthcFilter(): StatelessAccessControlFilter {
        return StatelessAccessControlFilter()
    }
    @Bean
    fun shiroFilterFactoryBean(securityManager: SecurityManager): ShiroFilterFactoryBean {
        val filterFactoryBean = ShiroFilterFactoryBean()

        filterFactoryBean.loginUrl = SecurityConstants.AUTH_WEB_PATH
        filterFactoryBean.successUrl = SecurityConstants.AUTH_LOGIN_SUCCESS_PATH
        filterFactoryBean.securityManager = securityManager
        filterFactoryBean.filters = filters()
        filterFactoryBean.filterChainDefinitionMap = shiroFilterChainDefinition().filterChainMap
        return filterFactoryBean
    }

    @Bean
    protected fun shiroFilterChainDefinition(): ShiroFilterChainDefinition {
        val chainDefinition = DefaultShiroFilterChainDefinition()
        chainDefinition.addPathDefinition("/login", "anon")
        chainDefinition.addPathDefinition("/logout", "logout")

        chainDefinition.addPathDefinition("/swagger-ui.html", "anon")
        chainDefinition.addPathDefinition("/swagger-resources/**", "anon")
        chainDefinition.addPathDefinition("/v2/api-docs/**", "anon")
        chainDefinition.addPathDefinition("/captcha.jpg", "anon")
        chainDefinition.addPathDefinition("/webjars/**", "anon")
        chainDefinition.addPathDefinition("/captcha", "anon")

        chainDefinition.addPathDefinition("/api/**", "authcApi")
        chainDefinition.addPathDefinition("/**", "authc")
        return chainDefinition
    }

    private fun filters(): Map<String, Filter> {
        val filters = HashMap<String, Filter>()
        filters["authcApi"] = statelessAuthcFilter()
        return filters
    }

    @Bean("lifecycleBeanPostProcessor")
    fun lifecycleBeanPostProcessor(): LifecycleBeanPostProcessor {
        return LifecycleBeanPostProcessor()
    }

    @Bean
    fun authorizationAttributeSourceAdvisor(securityManager: SecurityManager): AuthorizationAttributeSourceAdvisor {
        val advisor = AuthorizationAttributeSourceAdvisor()
        advisor.securityManager = securityManager
        return advisor
    }

    @Bean
    fun getDefaultAdvisorAutoProxyCreator(): DefaultAdvisorAutoProxyCreator {
        val daap = DefaultAdvisorAutoProxyCreator()
        daap.isProxyTargetClass = true
        return daap
    }

}