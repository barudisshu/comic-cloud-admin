package info.galudisu.comic.system.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.web.servlet.OncePerRequestFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenAuthFilter(private val config: JwtConfig) : OncePerRequestFilter() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req = request as HttpServletRequest
        val rsp = response as HttpServletResponse
        var token: String? = req.getHeader(config.header)
        if (token != null && token.startsWith(config.prefix + " ")) {
            token = token.replace(config.prefix + " ", "")
            var claims: Claims? = null
            // verify token
            try {
                claims = Jwts.parser()
                        .setSigningKey(config.secret)
                        .parseClaimsJws(token)
                        .body
            } catch (e: Exception) {
                logger.warn("jwt token verification failed", e)
            }

            // login automatically
            if (claims != null) {
                val principal = claims.subject
                val roles: List<String> = claims.get("roles", List::class.java as Class<List<String>>)
                val permissions: List<String> = claims.get("permissions", List::class.java as Class<List<String>>)
                try {
                    SecurityUtils.getSubject().login(JwtToken(principal, token, roles, permissions))
                } catch (e: AuthenticationException) {
                    logger.error("authentication failed", e)
                    rsp.status = HttpServletResponse.SC_UNAUTHORIZED
                    return
                }

            }
        }
        chain.doFilter(req, rsp)
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }
}