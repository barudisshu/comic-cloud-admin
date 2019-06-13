package info.galudisu.comic.system.security

import org.apache.shiro.authc.AuthenticationToken

class StatelessAuthenticationToken(val username: String, val clientDigest: String) : AuthenticationToken {

    override fun getPrincipal(): Any {
        return username
    }

    override fun getCredentials(): Any {
        return clientDigest
    }

}