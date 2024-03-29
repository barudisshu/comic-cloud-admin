package info.galudisu.comic.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
class EntityAlreadyExistException(message: String) : RuntimeException(message)