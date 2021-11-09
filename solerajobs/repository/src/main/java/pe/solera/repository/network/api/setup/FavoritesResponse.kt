package pe.solera.repository.network.api.setup

import com.google.gson.annotations.SerializedName
import pe.solera.entity.QuickAccessForSelection

data class FavoritesResponse(
    @SerializedName("id")
    var id: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("selected")
    var selected: Boolean?
)

fun FavoritesResponse.toEntity() : QuickAccessForSelection {
    return QuickAccessForSelection(
        id = this.id ?: String(),
        title = this.title ?: String(),
        description = this.description ?: String(),
        selected = this.selected ?: false
    )
}