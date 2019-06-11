package info.galudisu.comic.api.account

import info.galudisu.comic.model.tables.records.AccountsRecord
import org.apache.commons.codec.digest.Crypt
import org.apache.commons.codec.digest.DigestUtils
import java.time.Instant
import java.util.*

data class AccountsDto(val id: String, val username: String, val password: String, val email: String, val phone: String?, val createdAt: Instant)

fun AccountsRecord.toAccountsDto() = AccountsDto(
        id = uid, username = username, password = password, email = email, phone = phone, createdAt = createdAt.toInstant()
)

data class AccountsCreateRequest(val username: String, val rawPwd: String, val email: String, val phone: String?)

fun AccountsCreateRequest.toAccountsRecord(): AccountsRecord {
    val accountsRecord = AccountsRecord()
    val uid = UUID.randomUUID().toString()
    val salt = DigestUtils.sha1Hex(uid)
    accountsRecord.uid = uid
    accountsRecord.username = this.username
    accountsRecord.password = Crypt.crypt(this.rawPwd, salt)
    accountsRecord.salt = salt
    accountsRecord.email = this.email
    accountsRecord.phone = this.phone
    return accountsRecord
}

data class AccountsUpdateRequest(val username: String, val renewPwd: String)
