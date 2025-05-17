package octoping.ticketing.domain.users.service

import octoping.ticketing.domain.users.model.User
import octoping.ticketing.domain.users.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun createUser(username: String, email: String) {
        val user = User(username = username, email = email)
        userRepository.save(user)
    }

    fun findUserById(userId: Long): User {
        return userRepository.findById(userId)
            ?: throw IllegalArgumentException("User not found with id: $userId")
    }
}
