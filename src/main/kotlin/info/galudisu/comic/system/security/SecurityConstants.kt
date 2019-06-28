package info.galudisu.comic.system.security

object SecurityConstants {

    val AUTH_LOGIN_PATH = "/login"
    val AUTH_LOGIN_SUCCESS_PATH = "/home"
    val AUTH_UNAUTHORIZED_PATH = "/401"

    val HASH_ALGORITHM = "SHA-512"
    val HASH_INTERATIONS = 1024
    val SALT_SIZE = 16
}