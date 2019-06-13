package info.galudisu.comic.config

import info.galudisu.comic.system.servlet.CaptchaServlet
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class CaptchaConfig {

    @Bean(name = ["captchaServlet"])
    fun captchaServlet(): ServletRegistrationBean<*> {
        return ServletRegistrationBean(CaptchaServlet(), "/captcha")
    }
}