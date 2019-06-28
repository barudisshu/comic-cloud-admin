package info.galudisu.comic.utils

import info.galudisu.comic.system.user.UsersDto
import org.apache.commons.codec.binary.Hex
import org.apache.commons.lang3.Validate
import org.apache.shiro.crypto.hash.SimpleHash
import org.apache.shiro.util.ByteSource
import java.security.SecureRandom


object PasswordHelper {

    private const val ALGORITHM = "SHA-1"

    private const val HASHITERATIONS = 2

    private const val SALT_SIZE = 22

    private val random = SecureRandom()

    /**
     * 生成指定为数的随机的Byte[]作为salt.
     * @param numBytes
     * @return
     */
    fun generateSalt(numBytes: Int): ByteArray {
        Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes)

        val bytes = ByteArray(numBytes)
        random.nextBytes(bytes)
        return bytes
    }


    fun encodeHex(input: ByteArray): String {
        return String(Hex.encodeHex(input))
    }

    fun generateSalt(): String {
        val salt = generateSalt(SALT_SIZE)
        return encodeHex(salt)
    }

    fun encryptPassword(password: String, salt: String): String {
        return SimpleHash(
                ALGORITHM,
                password,
                ByteSource.Util.bytes(salt),
                HASHITERATIONS).toHex()
    }

    fun encryptPassword(user: UsersDto): String {
        return encryptPassword(user.password, user.salt)
    }

}