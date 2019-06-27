package info.galudisu.comic.rest

import info.galudisu.comic.exception.EntityAlreadyExistException
import info.galudisu.comic.exception.EntityNotFoundException
import info.galudisu.comic.exception.UnauthorizedException
import org.apache.shiro.ShiroException
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
internal class RestResponseEntityExceptionHandler {

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
    }

    @ModelAttribute
    fun addAttributes(model: Model) {
        model.addAttribute("author", "Magical Sam")
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = [ShiroException::class])
    fun unauthorized(e: ShiroException): ResponseInfo {
        return ResponseInfo(401, e.message, null)
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = [UnauthorizedException::class])
    fun unauthorized(): ResponseInfo {
        return ResponseInfo(401, "Unauthorized", null)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [EntityAlreadyExistException::class])
    fun entityAlreadyExist(e: EntityAlreadyExistException): ResponseInfo {
        return ResponseInfo(401, e.message, null)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [EntityNotFoundException::class])
    fun entityNotFound(e: EntityNotFoundException): ResponseInfo {
        return ResponseInfo(401, e.message, null)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [Exception::class])
    fun errorHandler(request: HttpServletRequest, ex: Exception): ResponseInfo {
        return ResponseInfo(getStatus(request).value(), ex.message, null)
    }

    private fun getStatus(request: HttpServletRequest): HttpStatus {
        val statusCode = request.getAttribute("javax.servlet.error.status_code") as Int
        return HttpStatus.valueOf(statusCode)
    }
}