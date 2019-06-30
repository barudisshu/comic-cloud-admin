package info.galudisu.comic.rest

import info.galudisu.comic.exception.EntityAlreadyExistException
import info.galudisu.comic.exception.EntityNotFoundException
import info.galudisu.comic.exception.UnauthorizedException
import org.apache.shiro.ShiroException
import org.apache.shiro.authz.AuthorizationException
import org.apache.shiro.authz.UnauthenticatedException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
internal class RestResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = [ShiroException::class])
    fun unauthorized(e: ShiroException): ResultInfo {
        return ResultInfo(401, e.message, null)
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = [UnauthorizedException::class])
    fun unauthorized(e: UnauthorizedException): ResultInfo {
        return ResultInfo(401, "Unauthorized: current subject has no permit", null)
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = [UnauthenticatedException::class])
    fun unauthorized(e: UnauthenticatedException): ResultInfo {
        return ResultInfo(401, "Unauthorized: current subject is anonymous", null)
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = [AuthorizationException::class])
    fun unauthorized(e: AuthorizationException): ResultInfo {
        return ResultInfo(403, "Forbidden: current subject is forbidden", null)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [EntityAlreadyExistException::class])
    fun entityAlreadyExist(e: EntityAlreadyExistException): ResultInfo {
        return ResultInfo(401, e.message, null)
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [EntityNotFoundException::class])
    fun entityNotFound(e: EntityNotFoundException): ResultInfo {
        return ResultInfo(401, e.message, null)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun globalException(request: HttpServletRequest, ex: Throwable): ResultInfo {
        return ResultInfo(this.getStatus(request).value(), ex.toString() + ": " + ex.message, null)
    }

    private fun getStatus(request: HttpServletRequest): HttpStatus {
        val statusCode = request.getAttribute("javax.servlet.error.status_code") as? Int
        return HttpStatus.valueOf(statusCode ?: 500)
    }
}