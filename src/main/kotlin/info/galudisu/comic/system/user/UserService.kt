package info.galudisu.comic.system.user

import info.galudisu.comic.system.user.repo.UserRepo
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepo: UserRepo) {

    fun findByUsername(username: String): UsersDto? =
            userRepo.getOneByUsername(username)?.toUserDto()

    fun findRoleCode(userId: String): List<String> =
            userRepo.getUserRoles(userId).map { it.code }

}