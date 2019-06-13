package info.galudisu.comic.system.security

import info.galudisu.comic.system.enums.AuthType
import info.galudisu.comic.system.enums.AuthType.Companion.getAuthType
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

    override fun assertCredentialsMatch(token: AuthenticationToken?, info: AuthenticationInfo?) {
        var skipPasswordCheck = true
        if (token is SecurityAuthenticationToken) {
            val authType = getAuthType(token.type)
            if (AuthType.WEB.type == authType.type || AuthType.API.type == authType.type) {
                skipPasswordCheck = false
            }
        }
        if (!skipPasswordCheck) {
            super.assertCredentialsMatch(token, info)
        }
    }
}