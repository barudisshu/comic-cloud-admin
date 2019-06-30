package info.galudisu.comic.api.account

import info.galudisu.comic.api.account.domain.repo.UsersRepo
import info.galudisu.comic.system.security.BCryptPasswordService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsersController(private val usersRepo: UsersRepo) {

    @GetMapping
    fun accountsFindAll() = usersRepo.getAllRecords().toList().map { it.toUsersDto() }

    @GetMapping("/{id}")
    fun accountsGetOne(@PathVariable id: String) = usersRepo.requireOneById(id).toUsersDto()

    @PutMapping
    fun accountsCreateOne(@RequestBody req: UsersCreateRequest) =
            req.toUsersRecord()
                    .let { usersRepo.insert(it) }
                    .toUsersDto()

    @PostMapping("/{id}")
    fun accountsUpdateOne(@PathVariable id: String, @RequestBody req: UsersUpdateRequest): UsersDto =
            usersRepo.requireOneById(id)
                    .apply {
                        username = req.username
                        password = BCryptPasswordService().encryptPassword(req.renewPwd)
                    }
                    .let { usersRepo.update(it) }
                    .toUsersDto()


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(UsersController::class.java)

    }
}