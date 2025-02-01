package octoping.ticketing.domain.seats.exception

import octoping.ticketing.domain.exception.DomainException

class SeatSoldOutException : DomainException("Seat is sold out")
