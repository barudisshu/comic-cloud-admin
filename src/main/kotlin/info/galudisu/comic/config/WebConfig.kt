package info.galudisu.comic.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter


@Configuration
class WebConfig {

    @Autowired
    lateinit var jackson2ObjectMapperBuilder: Jackson2ObjectMapperBuilder

    // these to fixed jackson properties issue
    @Bean
    fun mappingJackson2HttpMessageConverter(): MappingJackson2HttpMessageConverter {
        val mappingJackson2HttpMessageConverter = MappingJackson2HttpMessageConverter()
        mappingJackson2HttpMessageConverter.objectMapper = jackson2ObjectMapperBuilder.build()
        return mappingJackson2HttpMessageConverter
    }
}