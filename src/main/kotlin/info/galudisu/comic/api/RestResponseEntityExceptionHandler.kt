package info.galudisu.comic.api

import org.springframework.ui.Model
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*


@ControllerAdvice
internal class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
    }

    @ModelAttribute
    fun addAttributes(model: Model) {
        model.addAttribute("author", "Magical Sam")
    }

    @ResponseBody
    @ExceptionHandler(value = [Exception::class])
    fun errorHandler(ex: Exception): Map<*, *> {
        val map = HashMap<Any, Any?>()
        map.put("code", 100)
        map.put("msg", ex.javaClass.canonicalName)
        return map
    }
}