package app

import dao.UserDao
import model.User
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
open class UserController(private val dao: UserDao) {
    @GetMapping("version")
    open fun version(): String {
        return "API Version 1.0"
    }

    @GetMapping("users")
    @PreAuthorize("authentication.name == 'admin'")
    open fun getUsers(): List<User> {
        return dao.getAllUsers()
    }

    @GetMapping("users/{username}")
    @PreAuthorize("#username == authentication.name || authentication.name == 'admin'")
    open fun getUser(@PathVariable username: String): User {
        return dao.getUserByUsername(username)
    }
}