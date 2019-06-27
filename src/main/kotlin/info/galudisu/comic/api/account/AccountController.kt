package info.galudisu.comic.api.account

import info.galudisu.comic.api.account.domain.repo.AccountRepo
import org.apache.commons.codec.digest.Crypt
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountController(private val accountRepo: AccountRepo) {

    @GetMapping
    fun accountsFindAll() = accountRepo.getAllRecords().toList().map { it.toAccountDto() }

    @GetMapping("/{id}")
    fun accountsGetOne(@PathVariable id: String) = accountRepo.requireOneById(id).toAccountDto()

    @PutMapping
    fun accountsCreateOne(@RequestBody req: AccountsCreateRequest) =
            req.toAccountRecord()
                    .let { accountRepo.insert(it) }
                    .toAccountDto()

    @PostMapping("/{id}")
    fun accountsUpdateOne(@PathVariable id: String, @RequestBody req: AccountsUpdateRequest): AccountsDto =
            accountRepo.requireOneById(id)
                    .apply {
                        username = req.username
                        password = Crypt.crypt(req.renewPwd, salt)
                    }
                    .let { accountRepo.update(it) }
                    .toAccountDto()


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AccountController::class.java)

    }
}