package octoping.ticketing.domain.users.model

class User(
    id: Long = 0,
    username: String,
    email: String,
) {
    private val _id: Long = id
    private val _username = Username(username)
    private val _email = email

    val id: Long
        get() = _id

    val username: String
        get() = _username.value

    val email: String
        get() = _email

    fun isNew(): Boolean {
        return this._id == 0L
    }
}
