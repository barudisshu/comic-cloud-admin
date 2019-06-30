package info.galudisu.comic.system.security

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import info.galudisu.comic.rest.ResultInfo
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.realm.Realm
import org.apache.shiro.web.servlet.OncePerRequestFilter
import java.time.Instant
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class JwtUsernamePasswordAuthFilter(

        private val config: JwtConfig,
        private val realm: JdbcRealm,
        private val mapper: ObjectMapper

) : OncePerRequestFilter() {

    constructor(config: JwtConfig, realm: Realm) :
            this(config,
                    realm as JdbcRealm,
                    ObjectMapper().registerModule(KotlinModule()).setSerializationInclusion(JsonInclude.Include.NON_NULL))

    override fun isEnabled(request: ServletRequest?, response: ServletResponse?): Boolean {
        val req = request as HttpServletRequest
        return req.method.equals("POST", ignoreCase = true) && req.servletPath == config.url
    }

    override fun doFilterInternal(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val rsp = response as? HttpServletResponse
        val subject = SecurityUtils.getSubject()
        try {
            val user = mapper.readValue(request?.inputStream, JwtUser::class.java)
            subject.login(UsernamePasswordToken(user.username, user.password))
        } catch (e: Exception) {
            when (e) {
                is JsonProcessingException, is AuthenticationException -> {
                    rsp?.status = HttpServletResponse.SC_UNAUTHORIZED
                    rsp?.contentType = "application/json"
                    rsp?.writer!!.write(mapper.writeValueAsString(ResultInfo(401, "username or password unauthorized", null)))
                    return
                }
                else -> {
                    rsp?.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                    rsp?.contentType = "application/json"
                    rsp?.writer!!.write(mapper.writeValueAsString(ResultInfo(500, "internal server error", null)))
                    return
                }
            }
        }

        val now = Instant.now()
        val info = realm.getAuthorizationInfo(subject)
        val token = Jwts.builder()
                .setSubject(subject.principal as String)
                .claim("roles", info.roles)
                .claim("permissions", info.stringPermissions)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(config.expiration)))
                .signWith(SignatureAlgorithm.HS256, config.secret)
                .compact()
        rsp?.addHeader(config.header, config.prefix + " " + token)
    }
}