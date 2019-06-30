package info.galudisu.comic.system.security

import org.apache.shiro.authc.AuthenticationInfo
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.subject.PrincipalCollection
import org.apache.shiro.subject.SimplePrincipalCollection

class JwtToken(val principal: String,
               val token: String,
               val roles: List<String>,
               val permissions: List<String>) : AuthenticationToken {

    override fun getPrincipal(): Any {
        return principal
    }

    override fun getCredentials(): Any {
        return token
    }

    fun getAuthenticationInfo(realmName: String): AuthenticationInfo {
        return JwtAuthenticationInfo(realmName)
    }

    inner class JwtAuthenticationInfo(realmName: String) : AuthenticationInfo {

        private val principals: PrincipalCollection

        init {
            this.principals = JwtPrincipalCollection(realmName)
        }

        override fun getPrincipals(): PrincipalCollection {
            return principals
        }

        override fun getCredentials(): Any {
            return token
        }
    }

    inner class JwtPrincipalCollection(realmName: String) : SimplePrincipalCollection(principal, realmName) {

        fun getAuthorizationInfo(): AuthorizationInfo {
            val info = SimpleAuthorizationInfo()
            info.addRoles(roles)
            info.addStringPermissions(permissions)
            return info
        }
    }
}