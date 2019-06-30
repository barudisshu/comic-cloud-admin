package info.galudisu.comic.system.controller

import org.apache.shiro.authz.annotation.Logical
import org.apache.shiro.authz.annotation.RequiresRoles
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RoleBasedController {

    @GetMapping("/admin-only")
    @RequiresRoles("admin")
    fun adminOnly(): String {
        return "Hello Admin!"
    }

    @GetMapping("/user-only")
    @RequiresRoles("user")
    fun userOnly(): String {
        return "Hello User!"
    }

    @GetMapping("/admin-or-user")
    @RequiresRoles(logical = Logical.OR, value = ["admin", "user"])
    fun adminOrUser(): String {
        return "Hello Admin/User!"
    }

    @GetMapping("/public-to-all")
    fun publicToAll(): String {
        return "Hello Stranger!"
    }
}