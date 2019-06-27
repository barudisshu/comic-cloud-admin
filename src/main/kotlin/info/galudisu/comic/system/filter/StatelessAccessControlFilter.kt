package info.galudisu.comic.system.filter

import info.galudisu.comic.system.security.StatelessAuthenticationToken
import org.apache.shiro.web.filter.AccessControlFilter
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
import java.io.IOException
import java.util.HashMap
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class StatelessAccessControlFilter : BasicHttpAuthenticationFilter() {

    @Throws(Exception::class)
    override fun isAccessAllowed(request: ServletRequest, response: ServletResponse, mappedValue: Any): Boolean {
        return false
    }

    @Throws(Exception::class)
    override fun onAccessDenied(request: ServletRequest, response: ServletResponse): Boolean {
        val clientDigest = request.getParameter("clientDigest")
        val username = request.getParameter("username")

        val params = HashMap(request.parameterMap)
        params.remove("clientDigest")

        val token = StatelessAuthenticationToken(username, clientDigest)

        return try {
            getSubject(request, response).login(token)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            onLoginFail(response)
            false
        }

    }

    @Throws(IOException::class)
    private fun onLoginFail(response: ServletResponse) {
        val httpResponse = response as HttpServletResponse
        httpResponse.status = HttpServletResponse.SC_UNAUTHORIZED
        httpResponse.writer.write("login error")
    }
}