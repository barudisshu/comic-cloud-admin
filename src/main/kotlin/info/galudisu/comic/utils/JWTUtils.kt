package info.galudisu.comic.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTDecodeException
import java.io.UnsupportedEncodingException
import java.util.*

object JWTUtils {

    // time that will expire, hard code 5 minutes
    private const val EXPIRE_TIME: Long = 5 * 60 * 1000
    private const val CLAIM: String = "username"

    /**
     * verify token correct or not
     */
    fun verify(token: String, username: String, secret: String): Boolean {
        return try {
            val algorithm = Algorithm.HMAC256(secret)
            val verifier = JWT.require(algorithm)
                    .withClaim(CLAIM, username)
                    .build()
            verifier.verify(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUsername(token: String): String? {
        return try {
            JWT.decode(token).getClaim(CLAIM).asString()
        } catch (e: JWTDecodeException) {
            null
        }
    }

    /**
     * produce token with secret
     */
    fun sign(username: String, secret: String): String? {
        return try {
            val date = Date(System.currentTimeMillis() + EXPIRE_TIME)
            val algorithm = Algorithm.HMAC256(secret)
            JWT.create().withClaim(CLAIM, username).withExpiresAt(date).sign(algorithm)
        } catch (e: UnsupportedEncodingException) {
            null
        }
    }
}