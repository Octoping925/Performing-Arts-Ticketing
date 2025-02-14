package octoping.ticketing.domain.seats.repository

import octoping.ticketing.domain.seats.model.Seat

interface SeatRepository {
    fun findById(id: Long): Seat?
    fun save(seat: Seat): Seat
    fun saveAll(seats: List<Seat>): List<Seat>
}