package info.galudisu.comic.system.user.repo

import info.galudisu.comic.model.tables.Account
import info.galudisu.comic.model.tables.AccountRole
import info.galudisu.comic.model.tables.Role
import info.galudisu.comic.model.tables.records.AccountRecord
import info.galudisu.comic.model.tables.records.RoleRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class UserRepo(private val dsl: DSLContext) {

    companion object {
        private val ACCOUNT = Account.ACCOUNT
        private val ROLE = Role.ROLE
        private val ACCOUNT_ROLE = AccountRole.ACCOUNT_ROLE
    }

    fun getOneByUsername(username: String): AccountRecord? = dsl.select()
            .from(ACCOUNT)
            .where(ACCOUNT.USERNAME.likeIgnoreCase(username))
            .limit(1)
            .fetch()
            .into(AccountRecord::class.java)
            .firstOrNull()

    fun getUserRoles(userId: String): List<RoleRecord> = dsl.select()
            .from(ROLE)
            .leftJoin(ACCOUNT_ROLE).on(ACCOUNT_ROLE.ROLE_UID.eq(ROLE.UID))
            .where(ACCOUNT_ROLE.ACCOUNT_UID.eq(userId))
            .fetchInto(RoleRecord::class.java)


}