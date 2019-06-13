package info.galudisu.comic.system.security

object SecurityConstants {
    val AUTH_TYPE_SSO = "SSO"
    val AUTH_TYPE_WEB = "WEB"
    val AUTH_TYPE_API = "API"

    val AUTH_LOGIN_PATH = "/login"
    val AUTH_LOGIN_SUCCESS_PATH = "/home"
    val AUTH_WEB_PATH = "/auth/login"
    val AUTH_API_PATH = "/auth/api/login"
    val AUTH_SSO_PATH = "/auth/sso/login"

    val HASH_ALGORITHM = "SHA-512"
    val HASH_INTERATIONS = 1024
    val SALT_SIZE = 16
}