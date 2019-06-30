package info.galudisu.comic.api.role.domain.repo

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class RoleRepo(private val dsl: DSLContext) {

}