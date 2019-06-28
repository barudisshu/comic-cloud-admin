package info.galudisu.comic.system.security

import info.galudisu.comic.system.user.UserService
import info.galudisu.comic.utils.JWTUtils
import org.apache.commons.codec.digest.Crypt
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.AuthenticationInfo
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authc.SimpleAuthenticationInfo
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.apache.shiro.util.ByteSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate

class StatelessRealm(private val userService: UserService,
                     private val redisTemplate: RedisTemplate<*,*>) : AuthorizingRealm() {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(StatelessRealm::class.java)
    }

    override fun supports(token: AuthenticationToken?): Boolean {
        return token is StatelessAuthenticationToken
    }

    override fun doGetAuthorizationInfo(principals: PrincipalCollection?): AuthorizationInfo {
        logger.debug("doGetAuthorizationInfo......")

        val info = SimpleAuthorizationInfo()

        val securityUser = principals?.primaryPrincipal as SecurityUser
        val user = userService.findByUsername(securityUser.username)
        val roles = userService.findRoleCode(user?.id.orEmpty())

        info.addRoles(roles)

        return info
    }

    override fun doGetAuthenticationInfo(auth: AuthenticationToken?): AuthenticationInfo? {
        logger.debug("doGetAuthenticationInfo......")

        val token = auth?.credentials as String
        val username = JWTUtils.getUsername(token)

        username?.let {
            val user = userService.findByUsername(username)

            user?.let {
                when(JWTUtils.verify(token, username, user.password)) {
                    false -> throw AuthenticationException("Username or password error")
                    true -> {
                        val salt = ByteSource.Util.bytes(user.id)
                        return SimpleAuthenticationInfo(token, token, salt, user.username)
                    }
                }
            } ?: throw AuthenticationException("User did't existed!")
        } ?: throw AuthenticationException("token invalid")
    }

}