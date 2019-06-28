package info.galudisu.comic.system.filter

import info.galudisu.comic.system.security.StatelessAuthenticationToken
import info.galudisu.comic.utils.JWTUtils
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMethod
import java.io.IOException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class StatelessAccessControlFilter : BasicHttpAuthenticationFilter() {

    override fun isLoginAttempt(request: ServletRequest?, response: ServletResponse?): Boolean {
        val req = request as HttpServletRequest
        val authorization = req.getHeader("Authorization")
        return authorization != null
    }

    override fun executeLogin(request: ServletRequest?, response: ServletResponse?): Boolean {
        val req = request as HttpServletRequest
        val authorization = req.getHeader("Authorization")
        val token = StatelessAuthenticationToken(authorization)
        getSubject(request, response).login(token)
        return true
    }

    override fun isAccessAllowed(request: ServletRequest, response: ServletResponse, mappedValue: Any?): Boolean {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response)
            } catch (e: Exception) {
                response401(request, response)
            }

        }
        return true
    }

    override fun preHandle(request: ServletRequest?, response: ServletResponse?): Boolean {
        val httpServletRequest = request as HttpServletRequest
        val httpServletResponse = response as HttpServletResponse
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"))
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE")
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"))
        if (httpServletRequest.method == RequestMethod.OPTIONS.name) {
            httpServletResponse.status = HttpStatus.OK.value()
            return false
        }
        return super.preHandle(request, response)
    }

    @Throws(IOException::class)
    private fun onLoginFail(response: ServletResponse) {
        val httpResponse = response as HttpServletResponse
        httpResponse.status = HttpServletResponse.SC_UNAUTHORIZED
        httpResponse.writer.write("login error")
    }

    private fun response401(request: ServletRequest, response: ServletResponse) {
        try {
            val resp = response as HttpServletResponse
            resp.sendRedirect("/401")
        } catch (e: IOException) {
            logger.error(e.message)
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }
}