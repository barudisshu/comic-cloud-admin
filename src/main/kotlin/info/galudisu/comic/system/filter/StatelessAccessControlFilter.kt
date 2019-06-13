package info.galudisu.comic.system.filter

import info.galudisu.comic.system.security.StatelessAuthenticationToken
import org.apache.shiro.web.filter.AccessControlFilter
import java.io.IOException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletResponse

class StatelessAccessControlFilter : AccessControlFilter() {

    @Throws(Exception::class)
    override fun isAccessAllowed(request: ServletRequest, response: ServletResponse, mappedValue: Any): Boolean {
        return false
    }

    @Throws(Exception::class)
    override fun onAccessDenied(request: ServletRequest, response: ServletResponse): Boolean {
        val clientDigest = request.getParameter("clientDigest")
        val username = request.getParameter("username")

        val token = StatelessAuthenticationToken(username, clientDigest)

        try {
            getSubject(request, response).login(token)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            onLoginFail(response)

            return false
        }

    }

    @Throws(IOException::class)
    private fun onLoginFail(response: ServletResponse) {
        val httpResponse = response as HttpServletResponse
        httpResponse.status = HttpServletResponse.SC_UNAUTHORIZED
        httpResponse.writer.write("login error")
    }
}