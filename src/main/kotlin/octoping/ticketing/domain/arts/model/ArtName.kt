package octoping.ticketing.domain.arts.model

import octoping.ticketing.domain.exception.ValidationException

private const val MAX_NAME_LENGTH = 100

@JvmInline
value class ArtName(
    val name: String,
) {
    init {
        if (name.isBlank()) {
            throw ValidationException("Name cannot be blank: $name")
        }

        if (name.length > MAX_NAME_LENGTH) {
            throw ValidationException("Name cannot be longer than $MAX_NAME_LENGTH length: $name")
        }
    }
}
