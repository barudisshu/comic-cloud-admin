package info.galudisu.comic.utils

import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


object ServletUtils {

    //-- Content MsgType 定义 --//
    val TEXT_TYPE = "text/plain"
    val JSON_TYPE = "application/json"
    val XML_TYPE = "text/xml"
    val HTML_TYPE = "text/html"
    val JS_TYPE = "text/javascript"
    val EXCEL_TYPE = "application/vnd.ms-excel"

    //-- Header 定义 --//
    val AUTHENTICATION_HEADER = "Authorization"

    //-- 常用数值定义 --//
    val ONE_YEAR_SECONDS = (60 * 60 * 24 * 365).toLong()

    /**
     * 设置客户端缓存过期时间 Header.
     */
    fun setExpiresHeader(response: HttpServletResponse, expiresSeconds: Long) {
        // Http 1.0 header
        response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000)
        // Http 1.1 header
        response.setHeader("Cache-Control", "private, max-age=$expiresSeconds")
    }

    /**
     * 设置客户端无缓存Header.
     */
    fun setNoCacheHeader(response: HttpServletResponse) {
        // Http 1.0 header
        response.setDateHeader("Expires", 0)
        response.addHeader("Pragma", "no-cache")
        // Http 1.1 header
        response.setHeader("Cache-Control", "no-cache")
    }

    /**
     * 设置LastModified Header.
     */
    fun setLastModifiedHeader(response: HttpServletResponse, lastModifiedDate: Long) {
        response.setDateHeader("Last-Modified", lastModifiedDate)
    }

    /**
     * 设置Etag Header.
     */
    fun setEtag(response: HttpServletResponse, etag: String) {
        response.setHeader("ETag", etag)
    }

    /**
     * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
     *
     *
     * 如果无修改, checkIfModify返回false ,设置304 not modify status.
     *
     * @param lastModified 内容的最后修改时间.
     */
    fun checkIfModifiedSince(request: HttpServletRequest, response: HttpServletResponse,
                             lastModified: Long): Boolean {
        val ifModifiedSince = request.getDateHeader("If-Modified-Since")
        if (ifModifiedSince != -1L && lastModified < ifModifiedSince + 1000L) {
            response.status = HttpServletResponse.SC_NOT_MODIFIED
            return false
        }
        return true
    }

    /**
     * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
     *
     *
     * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
     *
     * @param etag 内容的ETag.
     */
    fun checkIfNoneMatchEtag(request: HttpServletRequest, response: HttpServletResponse, etag: String): Boolean {
        val headerValue = request.getHeader("If-None-Match")
        if (headerValue != null) {
            var conditionSatisfied = false
            if ("*" != headerValue) {
                val commaTokenizer = StringTokenizer(headerValue, ",")

                while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
                    val currentToken = commaTokenizer.nextToken()
                    if (currentToken.trim { it <= ' ' } == etag) {
                        conditionSatisfied = true
                    }
                }
            } else {
                conditionSatisfied = true
            }

            if (conditionSatisfied) {
                response.status = HttpServletResponse.SC_NOT_MODIFIED
                response.setHeader("ETag", etag)
                return false
            }
        }
        return true
    }

    /**
     * 设置让浏览器弹出下载对话框的Header.
     *
     * @param fileName 下载后的文件名.
     */
    fun setFileDownloadHeader(response: HttpServletResponse, fileName: String) {
        try {
            //中文文件名支持
            val encodedfileName = String(fileName.toByteArray(), Charset.forName("ISO8859-1"))
            response.setHeader("Content-Disposition", "attachment; filename=\"$encodedfileName\"")
        } catch (e: UnsupportedEncodingException) {
        }

    }

    /**
     * 取得带相同前缀的Request Parameters.
     *
     *
     * 返回的结果Parameter名已去除前缀.
     */
    fun getParametersStartingWith(request: HttpServletRequest, prefix: String?): Map<*, *> {
        var prefix = prefix
        val paramNames = request.parameterNames
        val params = TreeMap<String, Array<String>>()
        if (prefix == null) {
            prefix = ""
        }
        while (paramNames != null && paramNames.hasMoreElements()) {
            val paramName = paramNames.nextElement() as String
            if ("" == prefix || paramName.startsWith(prefix)) {
                val unprefixed = paramName.substring(prefix.length)
                val values: Array<String> = request.getParameterValues(paramName)
                when {
                    values.isEmpty() -> {
                    }
                    values.size > 1 -> params[unprefixed] = values
                }
            }
        }
        return params
    }
}