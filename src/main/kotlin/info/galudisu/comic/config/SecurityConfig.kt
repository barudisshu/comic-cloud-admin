package info.galudisu.comic.config

import info.galudisu.comic.system.security.*
import org.apache.shiro.authc.credential.PasswordMatcher
import org.apache.shiro.mgt.SecurityManager
import org.apache.shiro.realm.Realm
import org.apache.shiro.spring.LifecycleBeanPostProcessor
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class SecurityConfig {

    @Bean
    fun jwtUsernamePasswordAuthFilter(config: JwtConfig,
                                      @Qualifier("jdbcRealm") realm: Realm): JwtUsernamePasswordAuthFilter {
        return JwtUsernamePasswordAuthFilter(config, realm)
    }

    @Bean
    fun jwtTokenAuthFilter(config: JwtConfig): JwtTokenAuthFilter {
        return JwtTokenAuthFilter(config)
    }

    @Bean("jdbcRealm")
    fun jdbcRealm(dataSource: DataSource): Realm {
        val passwordMatcher = PasswordMatcher()
        passwordMatcher.passwordService = BCryptPasswordService()
        val jdbcRealm = JdbcRealm()
        jdbcRealm.setDataSource(dataSource)
        jdbcRealm.setPermissionsLookupEnabled(true)
        jdbcRealm.credentialsMatcher = passwordMatcher
        return jdbcRealm
    }

    @Bean("jwtTokenRealm")
    fun jwtTokenRealm(): Realm {
        return JwtTokenRealm()
    }

    @Bean
    fun webSecurityManager(realms: List<Realm>): DefaultWebSecurityManager {
        return DefaultWebSecurityManager(realms)
    }

    @Bean
    fun shiroFilterChainDefinition(config: JwtConfig): ShiroFilterChainDefinition {
        val chainDefinition = DefaultShiroFilterChainDefinition()

        // The following two lines indicate that:
        //   1. we need no session, because statelessness is preferred in REST world.
        //   2. use 'jwtUsernamePasswordAuth' to authenticate login request.
        //   3. use 'jwtTokenAuth' to authenticate all other requests.
        //   3. and leave authorization things to annotations in rest controllers.
        chainDefinition.addPathDefinition(config.url, "noSessionCreation, jwtUsernamePasswordAuth")
        chainDefinition.addPathDefinition("/**", "noSessionCreation, jwtTokenAuth")

        return chainDefinition
    }

    @Bean
    fun shiroFilterFactoryBean(securityManager: SecurityManager,
                               jwtUsernamePasswordAuthFilter: JwtUsernamePasswordAuthFilter,
                               jwtTokenAuthFilter: JwtTokenAuthFilter,
                               filterChainDefinition: ShiroFilterChainDefinition): ShiroFilterFactoryBean {
        val filterFactoryBean = ShiroFilterFactoryBean()
        filterFactoryBean.securityManager = securityManager
        filterFactoryBean.filters["jwtUsernamePasswordAuth"] = jwtUsernamePasswordAuthFilter
        filterFactoryBean.filters["jwtTokenAuth"] = jwtTokenAuthFilter
        filterFactoryBean.filterChainDefinitionMap = filterChainDefinition.filterChainMap
        return filterFactoryBean
    }

    @Bean(name = ["lifecycleBeanPostProcessor"])
    fun getLifecycleBeanPostProcessor(): LifecycleBeanPostProcessor {
        return LifecycleBeanPostProcessor()
    }

    @Bean
    fun getDefaultAdvisorAutoProxyCreator(): DefaultAdvisorAutoProxyCreator {
        val autoProxyCreator = DefaultAdvisorAutoProxyCreator()
        autoProxyCreator.isUsePrefix = true
        autoProxyCreator.isProxyTargetClass = true
        return autoProxyCreator
    }

    @Bean
    fun getAuthorizationAttributeSourceAdvisor(
            securityManager: DefaultWebSecurityManager): AuthorizationAttributeSourceAdvisor {
        val advisor = AuthorizationAttributeSourceAdvisor()
        advisor.securityManager = securityManager
        return advisor
    }
}