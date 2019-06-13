package info.galudisu.comic.system.security

import org.apache.shiro.authc.UsernamePasswordToken

class SecurityAuthenticationToken(val captcha: String, val reference: String, val type: String): UsernamePasswordToken() {
}