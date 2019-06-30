package info.galudisu.comic.system.controller

import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PermissionBasedController {

    @PostMapping("/read")
    @RequiresPermissions("files:read")
    fun read(): String {
        return "read files"
    }

    @PostMapping("/write")
    @RequiresPermissions("files:write")
    fun write(): String {
        return "write files"
    }

    @PostMapping("/archive")
    @RequiresPermissions("files:archive")
    fun archive(): String {
        return "archive files"
    }

    @PostMapping("/read-log")
    @RequiresPermissions("files:read:log")
    fun readLog(): String {
        return "read log files"
    }

    @PostMapping("/write-log")
    @RequiresPermissions("files:write:log")
    fun writeLog(): String {
        return "write log files"
    }

    @PostMapping("/archive-log")
    @RequiresPermissions("files:archive:log")
    fun archiveLog(): String {
        return "archive log files"
    }
}