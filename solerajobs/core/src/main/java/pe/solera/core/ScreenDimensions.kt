package pe.solera.core

import android.content.Context
import pe.solera.core.extension.CoreSizeType
import pe.solera.core.extension.screenSize

class ScreenDimensions(
    private val context: Context
) {

    var height : Int = 0
    var width : Int = 0

    init {
        this.height = context.screenSize(CoreSizeType.HEIGHT)
        this.width = context.screenSize(CoreSizeType.WIDTH)
    }
}