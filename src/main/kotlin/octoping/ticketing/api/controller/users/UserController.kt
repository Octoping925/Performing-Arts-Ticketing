package octoping.ticketing.api.controller.users

import octoping.ticketing.domain.users.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
) {
    // fixme: 테스트 편의를 위해 그냥 GET으로 구현
    @GetMapping("/users")
    fun createUser(
        @RequestParam username: String,
    ) {
        userService.createUser(username)
    }
}
