package info.galudisu.comic.api

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class ApiConfig(
        @Value(value = "\${spring.application.name}") val appName: String
) {
    val title: String
        get() = "API $appName"
}