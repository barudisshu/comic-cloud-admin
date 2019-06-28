package info.galudisu.comic.system.servlet

import com.google.code.kaptcha.impl.DefaultKaptcha
import com.google.code.kaptcha.util.Config
import sun.misc.BASE64Encoder
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO
import javax.servlet.ServletConfig
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CaptchaServlet : HttpServlet() {

    @Throws(ServletException::class)
    override fun init(config: ServletConfig) {
        super.init(config)
    }

    fun kaptcha(): DefaultKaptcha {
        val captchaProducer = DefaultKaptcha()
        val properties = Properties()
        properties.setProperty("kaptcha.border", "yes")
        properties.setProperty("kaptcha.border.color", "105,179,230")
        properties.setProperty("kaptcha.textproducer.font.color", "blue")
        properties.setProperty("kaptcha.image.width", "110")
        properties.setProperty("kaptcha.image.height", "40")
        properties.setProperty("kaptcha.textproducer.font.size", "30")
        properties.setProperty("kaptcha.session.key", "code")
        properties.setProperty("kaptcha.textproducer.char.length", "4")
        val config = Config(properties)
        captchaProducer.config = config
        return captchaProducer
    }


    @Throws(IOException::class, ServletException::class)
    override fun doGet(req: HttpServletRequest, response: HttpServletResponse) {
        response.setHeader("Cache-Control", "no-cache")
        response.setDateHeader("Expires", 0)
        response.setHeader("Pragma", "no-cache")
        response.setDateHeader("Max-Age", 0)

        val kaptcha = kaptcha()
        val createText = kaptcha.createText()

        val image = kaptcha.createImage(createText)

        val outputStream = ByteArrayOutputStream()
        ImageIO.write(image, "jpeg", outputStream)

        val base64Image = BASE64Encoder().encodeBuffer(outputStream.toByteArray())

        response.writer.write(base64Image)

        outputStream.close()
    }
}