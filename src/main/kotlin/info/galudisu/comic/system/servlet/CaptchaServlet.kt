package info.galudisu.comic.system.servlet

import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO
import javax.servlet.ServletConfig
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 简单的验证码生成器
 */
class CaptchaServlet : HttpServlet() {

    private val width = 120
    private val height = 30

    @Throws(ServletException::class)
    override fun init(config: ServletConfig) {
        super.init(config)
    }

    private fun getRandColor(fc: Int, bc: Int): Color {
        var fc = fc
        var bc = bc
        val random = Random()
        if (fc > 255) fc = 255
        if (bc > 255) bc = 255
        val r = fc + random.nextInt(bc - fc)
        val g = fc + random.nextInt(bc - fc)
        val b = fc + random.nextInt(bc - fc)
        return Color(r, g, b)
    }

    @Throws(IOException::class, ServletException::class)
    override fun doGet(req: HttpServletRequest, response: HttpServletResponse) {
        response.setHeader("Cache-Control", "no-cache")
        response.setDateHeader("Expires", 0)
        response.setHeader("Pragma", "no-cache")
        response.setDateHeader("Max-Age", 0)
        response.contentType = "image/jpeg"

        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val font = Font("Verdana", Font.CENTER_BASELINE, 20)
        val c = getRandColor(200, 250)

        val g = image.graphics
        g.color = c
        g.fillRect(1, 1, width - 1, height - 1)
        g.color = Color(102, 102, 102)
        g.drawRect(0, 0, width - 1, height - 1)
        g.font = font

        val random = Random()

        // 画随机线
        for (i in 0..154) {
            val x = random.nextInt(width - 1)
            val y = random.nextInt(height - 1)
            val xl = random.nextInt(6) + 1
            val yl = random.nextInt(12) + 1
            g.drawLine(x, y, x + xl, y + yl)
        }

        // 从另一方向画随机线
        for (i in 0..69) {
            val x = random.nextInt(width - 1)
            val y = random.nextInt(height - 1)
            val xl = random.nextInt(12) + 1
            val yl = random.nextInt(6) + 1
            g.drawLine(x, y, x - xl, y - yl)
        }

        // 生成随机数,并将随机数字转换为字母
        var sRand = ""
        for (i in 0..5) {
            val itmp = random.nextInt(26) + 65
            val ctmp = itmp.toChar()
            sRand += ctmp.toString()
            g.color = Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110))
            g.drawString(ctmp.toString(), 15 * i + 10, 16)
        }
        g.dispose()

        val session = req.session
        session.setAttribute(CAPTCHA, sRand)

        val outputStream = response.outputStream
        ImageIO.write(image, "jpeg", outputStream)
        outputStream.close()
    }

    companion object {
        private val serialVersionUID: Long = -1

        val CAPTCHA = "captcha"
    }
}