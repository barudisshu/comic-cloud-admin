package info.galudisu.comic.system.security

import org.apache.shiro.authc.credential.PasswordService
import org.mindrot.jbcrypt.BCrypt

class BCryptPasswordService : PasswordService {
    override fun encryptPassword(plaintextPassword: Any): String {
        val str: String =
                when (plaintextPassword) {
                    is String -> plaintextPassword
                    is CharArray -> String(plaintextPassword)
                    is ByteArray -> String(plaintextPassword)
                    else -> throw IllegalArgumentException("Unsupported password type: " + plaintextPassword.javaClass.name)
                }
        return BCrypt.hashpw(str, BCrypt.gensalt())
    }

    override fun passwordsMatch(submittedPlaintext: Any?, encrypted: String?): Boolean {
        return BCrypt.checkpw(String(submittedPlaintext as CharArray), encrypted)
    }
}