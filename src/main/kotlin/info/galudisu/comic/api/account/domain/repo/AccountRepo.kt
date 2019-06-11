package info.galudisu.comic.api.account.domain.repo

import info.galudisu.comic.exception.EntityNotFoundException
import info.galudisu.comic.model.tables.Accounts
import info.galudisu.comic.model.tables.records.AccountsRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class AccountRepo(private val dsl: DSLContext) {
    fun getAllRecords(): List<AccountsRecord> = dsl.select()
            .from(ACCOUNTS)
            .fetch()
            .into(AccountsRecord::class.java)

    fun getOneById(id: String): AccountsRecord? = dsl.select()
            .from(ACCOUNTS)
            .where(ACCOUNTS.UID.eq(id))
            .limit(1)
            .fetch()
            .into(AccountsRecord::class.java)
            .firstOrNull()

    fun requireOneById(id: String): AccountsRecord = getOneById(id) ?: throw EntityNotFoundException("AccountsRecord NOT FOUND ! (id=$id)")

    fun insert(accountsRecord: AccountsRecord) = dsl.insertInto(ACCOUNTS)
            .set(accountsRecord)
            .execute()
            .let { requireOneById(accountsRecord.uid) }

    fun update(accountsRecord: AccountsRecord) = dsl.update(ACCOUNTS)
            .set(accountsRecord)
            .execute()
            .let { requireOneById(accountsRecord.uid) }

    companion object {
        private val ACCOUNTS = Accounts.ACCOUNTS
    }
}