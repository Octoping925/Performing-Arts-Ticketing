package octoping.ticketing.persistence.model.users

import octoping.ticketing.domain.users.model.User
import octoping.ticketing.domain.users.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserRepository {
    override fun findById(id: Long): User? = userJpaRepository.findByIdOrNull(id)?.toUser()

    override fun save(user: User): User =
        UserEntity.from(user).let {
            userJpaRepository.save(it).toUser()
        }

    override fun saveAll(users: List<User>): List<User> =
        userJpaRepository.saveAll(users.map(UserEntity::from)).map(UserEntity::toUser)
}
