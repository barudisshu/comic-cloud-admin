package info.galudisu.comic.api.account.domain.repo

import info.galudisu.comic.exception.EntityAlreadyExistException
import info.galudisu.comic.exception.EntityNotFoundException
import info.galudisu.comic.model.tables.Users
import info.galudisu.comic.model.tables.records.UsersRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class UsersRepo(private val dsl: DSLContext) {
    fun getAllRecords(): List<UsersRecord> = dsl.select()
            .from(USERS)
            .fetch()
            .into(UsersRecord::class.java)

    fun getOneById(id: String): UsersRecord? = dsl.select()
            .from(USERS)
            .where(USERS.UID.eq(id))
            .limit(1)
            .fetch()
            .into(UsersRecord::class.java)
            .firstOrNull()

    fun getOneByUsername(username: String): UsersRecord? = dsl.select()
            .from(USERS)
            .where(USERS.USERNAME.eq(username))
            .limit(1)
            .fetch()
            .into(UsersRecord::class.java)
            .firstOrNull()

    fun requireOneById(id: String): UsersRecord = getOneById(id)
            ?: throw EntityNotFoundException("UsersRecord NOT FOUND ! (id=$id)")

    fun insert(accountsRecord: UsersRecord) =
            getOneByUsername(accountsRecord.username)?.let {
                throw EntityAlreadyExistException("UsersRecord username exists ! (username=${accountsRecord.username})")
            } ?: run {
                dsl.insertInto(USERS)
                        .set(accountsRecord)
                        .execute()
                        .let { requireOneById(accountsRecord.uid) }
            }


    fun update(accountsRecord: UsersRecord) = dsl.update(USERS)
            .set(accountsRecord)
            .execute()
            .let { requireOneById(accountsRecord.uid) }

    companion object {
        private val USERS = Users.USERS
    }
}