package octoping.ticketing.api.controller.arts.request

import octoping.ticketing.api.controller.arts.schema.ArtItemSchema

data class ArtListResponseDTO(
    val arts: List<ArtItemSchema>,
    val page: Int,
) {
}
