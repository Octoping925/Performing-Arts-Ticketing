package octoping.ticketing.domain.users.exception

import octoping.ticketing.domain.exception.DomainException

class UnauthorizedException(
    message: String = "권한이 없습니다",
) : DomainException(message)
