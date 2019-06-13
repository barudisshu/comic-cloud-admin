package info.galudisu.comic.system.security

import info.galudisu.comic.system.user.UserService
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

class SecurityRealm(private val userService: UserService) : AuthorizingRealm() {


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(SecurityRealm::class.java)
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

    override fun doGetAuthenticationInfo(token: AuthenticationToken?): AuthenticationInfo? {
        logger.debug("doGetAuthenticationInfo......")
        val info: SimpleAuthenticationInfo
        val user = userService.findByUsername(token?.principal.toString())

        if (user != null) {
            val securityUser = SecurityUser(user.username, user.username)
            info = SimpleAuthenticationInfo(securityUser, user.password, user.username)
            info.credentialsSalt = ByteSource.Util.bytes(user.salt)
            return info
        }
        return null
    }

}