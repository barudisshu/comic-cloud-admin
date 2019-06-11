package info.galudisu.comic.api.account

import info.galudisu.comic.api.account.domain.repo.AccountRepo
import org.apache.commons.codec.digest.Crypt
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountController(
        private val accountRepo: AccountRepo
) {

    private var logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping
    fun accountsFindAll() = accountRepo.getAllRecords().toList().map { it.toAccountsDto() }

    @GetMapping("/{id}")
    fun accountsGetOne(@PathVariable id: String) = accountRepo.requireOneById(id).toAccountsDto()

    @PutMapping
    fun accountsCreateOne(@RequestBody req: AccountsCreateRequest) =
            req.toAccountsRecord()
                    .let { accountRepo.insert(it) }
                    .also { logger.info("update Record: {}", it) }
                    .toAccountsDto()

    @PostMapping("/{id}")
    fun accountsUpdateOne(@PathVariable id: String, @RequestBody req: AccountsUpdateRequest): AccountsDto =
            accountRepo.requireOneById(id)
                    .apply {
                        username = req.username
                        password = Crypt.crypt(req.renewPwd, salt)
                    }
                    .let { accountRepo.update(it) }
                    .also { logger.info("Updated Record: {}", it) }
                    .toAccountsDto()
}