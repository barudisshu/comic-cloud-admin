package info.galudisu.comic.api.account

import info.galudisu.comic.model.tables.records.UsersRecord
import info.galudisu.comic.system.security.BCryptPasswordService
import java.time.Instant
import java.util.*

data class UsersDto(val id: String, val username: String, val password: String, val email: String, val phone: String?, val createdAt: Instant)

fun UsersRecord.toUsersDto() = UsersDto(
        id = uid, username = username, password = password, email = email, phone = phone, createdAt = createdAt
)

data class UsersCreateRequest(val username: String, val rawPwd: String, val email: String, val phone: String?)

fun UsersCreateRequest.toUsersRecord(): UsersRecord {
    val accountsRecord = UsersRecord()
    val uid = UUID.randomUUID().toString()
    accountsRecord.uid = uid
    accountsRecord.username = this.username
    accountsRecord.password = BCryptPasswordService().encryptPassword(rawPwd)
    accountsRecord.email = this.email
    accountsRecord.phone = this.phone
    return accountsRecord
}

data class UsersUpdateRequest(val username: String, val renewPwd: String)

