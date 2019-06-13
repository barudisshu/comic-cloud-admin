import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Strings
import info.galudisu.comic.Constants
import info.galudisu.comic.utils.ServletUtils
import java.io.IOException
import java.net.InetAddress
import java.net.UnknownHostException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object WebUtils {

    private val mapper = ObjectMapper()

    /**
     * 判断当前请求是否是Ajax请求
     *
     * @param request HttpServletRequest
     * @return boolean
     */
    fun isAjaxRequest(request: HttpServletRequest): Boolean {
        return request.getHeader("X-Requested-With") != null && "XMLHttpRequest" == request.getHeader("X-Requested-With")
    }

    /**
     * 判断当前请求是否是JSONP请求
     *
     * @param request HttpServletRequest
     * @param param   String
     * @return boolean
     */
    fun isJsonpRequest(request: HttpServletRequest, param: String): Boolean {
        return request.getParameter(param) != null
    }

    fun isJsonpCallback(request: HttpServletRequest, param: String): String {
        return request.getParameter(param)
    }

    /**
     * 获取Referer
     *
     * @param request HttpServletRequest
     * @return String
     */
    fun getReferer(request: HttpServletRequest): String {
        return request.getHeader("REFERER")
    }

    /**
     * 获取提交方式
     *
     * @param request HttpServletRequest
     * @return String
     */
    fun getMethod(request: HttpServletRequest): String {
        return request.method
    }

    /**
     * 获取客户端IP
     *
     * @param request HttpServletRequest
     * @return String
     */

    fun getHost(request: HttpServletRequest): String? {
        var ip: String? = request.getHeader("X-Forwarded-For")
        if (Strings.isNullOrEmpty(ip) || "unknown".equals(ip!!, ignoreCase = true)) {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (Strings.isNullOrEmpty(ip) || "unknown".equals(ip!!, ignoreCase = true)) {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if (Strings.isNullOrEmpty(ip) || "unknown".equals(ip!!, ignoreCase = true)) {
            ip = request.getHeader("X-Real-IP")
        }
        if (Strings.isNullOrEmpty(ip) || "unknown".equals(ip!!, ignoreCase = true)) {
            ip = request.remoteAddr
        }
        if ("127.0.0.1" == ip) {
            val inet: InetAddress
            try { // 根据网卡取本机配置的IP
                inet = InetAddress.getLocalHost()
                ip = inet.hostAddress
            } catch (e: UnknownHostException) {
                e.printStackTrace()
            }

        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","))
            }
        }
        return ip
    }

    /**
     * 直接输出内容的简便函数.
     */
    fun render(response: HttpServletResponse, contentType: String, content: String, headers: Array<String>) {
        var response = response
        response = initResponseHeader(response, contentType, headers)
        try {
            response.writer.write(content)
            response.writer.flush()
        } catch (e: IOException) {
            throw RuntimeException(e.message, e)
        }

    }

    /**
     * 直接输出文本.
     */
    fun renderText(response: HttpServletResponse, text: String, headers: Array<String>) {
        render(response, ServletUtils.TEXT_TYPE, text, headers)
    }

    /**
     * 直接输出HTML.
     */
    fun renderHtml(response: HttpServletResponse, html: String, headers: Array<String>) {
        render(response, ServletUtils.HTML_TYPE, html, headers)
    }

    /**
     * 直接输出XML.
     */
    fun renderXml(response: HttpServletResponse, xml: String, headers: Array<String>) {
        render(response, ServletUtils.XML_TYPE, xml, headers)
    }

    /**
     * 直接输出JSON.
     */
    fun renderJson(response: HttpServletResponse, jsonString: String, headers: Array<String>) {
        render(response, ServletUtils.JSON_TYPE, jsonString, headers)
    }

    /**
     * 直接输出JSON,使用Jackson转换Java对象.
     */
    fun renderJson(response: HttpServletResponse, data: Any, headers: Array<String>) {
        var response = response
        response = initResponseHeader(response, ServletUtils.JSON_TYPE, headers)
        try {
            mapper.writeValue(response.writer, data)
        } catch (e: IOException) {
            throw IllegalArgumentException(e)
        }

    }

    /**
     * 直接输出支持跨域Mashup的JSONP.
     */
    fun renderJsonp(response: HttpServletResponse, callbackName: String, `object`: Any, headers: Array<String>) {
        var jsonString: String? = null
        try {
            jsonString = mapper.writeValueAsString(`object`)
        } catch (e: IOException) {
            throw IllegalArgumentException(e)
        }

        val result = "$callbackName($jsonString);"

        render(response, ServletUtils.JS_TYPE, result, headers)
    }

    private fun initResponseHeader(response: HttpServletResponse, contentType: String, headers: Array<String>): HttpServletResponse {
        // 设置headers参数
        val fullContentType = contentType + "; charset=" + Constants.ENCODING
        response.contentType = fullContentType
        ServletUtils.setNoCacheHeader(response)
        return response
    }
}