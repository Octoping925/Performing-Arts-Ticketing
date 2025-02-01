package octoping.ticketing.domain.arts.model

import octoping.ticketing.domain.exception.ValidationException
import java.time.LocalDate

class Art(
    id: Long,
    name: String,
    description: String,
    onePersonBuyLimit: Int,
    startDate: LocalDate,
    endDate: LocalDate? = null,
) {
    private val _id: Long
    private var _name: ArtName
    private var _description: String
    private var _onePersonBuyLimit: Int
    private var artDate: ArtDate

    init {
        validate(description)
        this._id = id
        this._name = ArtName(name)
        this._description = description
        this._onePersonBuyLimit = onePersonBuyLimit
        this.artDate = ArtDate(startDate, endDate)
    }

    val id get() = _id
    val name get() = _name.name
    val description get() = _description
    val onePersonBuyLimit get() = _onePersonBuyLimit

    val startDate get() = artDate.startDate
    val endDate get() = artDate.endDate

    private fun validate(description: String) {
        if (description.length > MAX_DESCRIPTION_LENGTH) {
            throw ValidationException("Description cannot be greater than max description: $description")
        }
    }

    companion object {
        private const val MAX_DESCRIPTION_LENGTH = 100000
    }
}
