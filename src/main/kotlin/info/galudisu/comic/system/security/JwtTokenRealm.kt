package info.galudisu.comic.system.security

import org.apache.shiro.authc.AuthenticationInfo
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection

class JwtTokenRealm : AuthorizingRealm() {

    override fun supports(token: AuthenticationToken?): Boolean {
        return token is JwtToken
    }

    override fun doGetAuthenticationInfo(token: AuthenticationToken): AuthenticationInfo {
        return (token as JwtToken).getAuthenticationInfo("jwtTokenRealm")
    }

    override fun doGetAuthorizationInfo(principals: PrincipalCollection): AuthorizationInfo {
        return (principals as JwtToken.JwtPrincipalCollection).getAuthorizationInfo()
    }
}