package info.galudisu.comic.system.user

import info.galudisu.comic.model.tables.records.AccountRecord
import java.time.Instant

data class UsersDto(val id: String, val username: String, val password: String, val salt: String, val email: String, val phone: String?, val createdAt: Instant)


fun AccountRecord.toUserDto() = UsersDto(
        id = uid, username = username, password = password, salt = salt, email = email, phone = phone, createdAt = createdAt)