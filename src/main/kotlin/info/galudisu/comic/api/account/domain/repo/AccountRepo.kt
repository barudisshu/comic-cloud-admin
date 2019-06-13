package info.galudisu.comic.api.account.domain.repo

import info.galudisu.comic.exception.EntityNotFoundException
import info.galudisu.comic.model.tables.Account
import info.galudisu.comic.model.tables.records.AccountRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class AccountRepo(private val dsl: DSLContext) {
    fun getAllRecords(): List<AccountRecord> = dsl.select()
            .from(ACCOUNT)
            .fetch()
            .into(AccountRecord::class.java)

    fun getOneById(id: String): AccountRecord? = dsl.select()
            .from(ACCOUNT)
            .where(ACCOUNT.UID.eq(id))
            .limit(1)
            .fetch()
            .into(AccountRecord::class.java)
            .firstOrNull()

    fun requireOneById(id: String): AccountRecord = getOneById(id) ?: throw EntityNotFoundException("AccountRecord NOT FOUND ! (id=$id)")

    fun insert(accountsRecord: AccountRecord) = dsl.insertInto(ACCOUNT)
            .set(accountsRecord)
            .execute()
            .let { requireOneById(accountsRecord.uid) }

    fun update(accountsRecord: AccountRecord) = dsl.update(ACCOUNT)
            .set(accountsRecord)
            .execute()
            .let { requireOneById(accountsRecord.uid) }

    companion object {
        private val ACCOUNT = Account.ACCOUNT
    }
}