package pe.solera.repository.network.api.task

import com.google.gson.annotations.SerializedName
import pe.solera.entity.QuickAccess
import pe.solera.entity.QuickAccessItem

data class BulletsResponse(
    @SerializedName("name")
    var name: String,
    @SerializedName("client")
    var client: BulletItemResponse = BulletItemResponse(),
    @SerializedName("project")
    var project: BulletItemResponse = BulletItemResponse()
)

data class BulletItemResponse(
    @SerializedName("id")
    var id: Int = -1,
    @SerializedName("name")
    var name: String = String()
)

fun BulletsResponse.toEntity() : QuickAccess {
    return QuickAccess(
        name = this.name,
        client = QuickAccessItem(
            this.client.id,
            this.client.name
        ),
        project = QuickAccessItem(
            this.project.id,
            this.project.name
        )
    )
}
