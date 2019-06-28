package info.galudisu.comic.api.entry

import info.galudisu.comic.exception.EntityNotFoundException
import info.galudisu.comic.rest.ResponseInfo
import info.galudisu.comic.system.user.UserService
import info.galudisu.comic.utils.JWTUtils
import org.apache.commons.codec.digest.Crypt
import org.apache.shiro.authz.UnauthorizedException
import org.apache.shiro.authz.annotation.RequiresAuthentication
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class LoginController(val userService: UserService) {

    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto): ResponseInfo {
        val user = userService.findByUsername(loginDto.username)
        user?.let {
            val encodePass = Crypt.crypt(loginDto.password, it.salt)
            if (it.password == encodePass) {
                return ResponseInfo(200, "Login success", JWTUtils.sign(it.username, encodePass))
            } else {
                throw UnauthorizedException()
            }
        } ?: throw EntityNotFoundException("username=${loginDto.username} not found")
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    fun requireAuth(): ResponseInfo {
        return ResponseInfo(200, "You are authenticated", null)
    }

    @RequestMapping("/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun unauthorized(): ResponseInfo {
        return ResponseInfo(401, "Unauthorized", null)
    }
}