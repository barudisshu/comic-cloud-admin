package info.galudisu.comic.system.security

import org.apache.shiro.authc.AuthenticationToken

class StatelessAuthenticationToken(val token: String) : AuthenticationToken {

    override fun getPrincipal(): Any {
        return token
    }

    override fun getCredentials(): Any {
        return token
    }

}