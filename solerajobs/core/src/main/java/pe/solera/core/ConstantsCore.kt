package pe.solera.core

import java.util.*

object ConstantsCore {
    const val empty              : String = ""
    const val defaultCode        : Int    = -1
    const val problemLog         : String = "PROBLEM_LOG"
    const val release_package    : String = "pe.solera.solerajobs"
    const val app_playstore_url  : String = "https://play.google.com/store/apps/details?id=${release_package}&hl=en"
    const val app_maps_store_url : String = "http://play.google.com/store/apps/details?id=com.google.android.apps.maps"
    const val loading            : String = "Cargando..."
    object Commons {
        const val DRAWABLE = "drawable"
        const val MIPMAP   = "mipmap"
        const val STRING   = "string"
    }
    object Packages {
        const val waze = "com.waze"
        const val whatsapp = "com.whatsapp"
        const val googleMaps = "com.google.android.apps.maps"
    }
    object RequestCode {
        const val PHONE : Int = 200
        const val SMS : Int = 201
        const val GPS : Int = 202
        const val GPS_ENABLED : Int = 203
        const val WRITE_STORAGE : Int = 204
        const val CAMERA : Int = 205
    }
    object IntentFormat {
        fun phone(phone: String) = "tel:${phone.trim()}"
    }
    object BuildTypes {
        const val release : String = "release"
        const val debug   : String = "debug"
    }
    object Error {
        object Code {
            const val generalErrorCode : Int = -1
            const val unAuthorizeCode  : Int = 401
        }
        object Message {
            const val nullErrorMessage     : String = "Contenido inválido."
            const val generalErrorMessage  : String = "Ha ocurrido un error desconocido."
            const val errorNotification    : String = "Error Notification"
            const val invalidValueType     : String = "Tipo de dato inválido"
            const val fragmentError        : String = "Ha ocurrido un error en la transacción de fragments"
            const val cantShowKeyboard     : String = "No se ha podido mostrar el teclado"
            const val invalidUrl           : String = "Invalid URL"
            const val airplaneError        : String = "Desactivar el modo avión para continuar."
            const val errorNetwork         : String = "Hay un problema de conexión. Por favor intente en unos minutos."
            const val resourceErrorMessage : String = "Hubo un problema obteniendo el recurso"
            const val unAuthorizeMessage   : String = "Sesión no autorizada."
        }
        object View {
            const val cantFindView         : String = "No se pudo encontrar view"
            const val cantModifyView       : String = "No se pudo modificar view"
            const val cantModifyEditText   : String = "No se pudo modificar editText"
            const val cantModifyMap        : String = "No se pudo modificar mapa"
            const val cantShowDialog       : String = "No se pudo mostrar dialog"
            const val cantModifyBackground : String = "No se pudo modificar background"
            const val cantModifyImage      : String = "No se pudo modificar imagen"
            const val cantModifyTextColor  : String = "No se pudo modificar textColor"
            const val cantModifyText       : String = "No se pudo modificar el texto"
            const val cantDelayButtonClick : String = "No se pudo bloquear el botón"
            const val cantModifyTopBar     : String = "No se pudo modificar topBar"
            const val cantModifyBottomNav  : String = "No se pudo modificar BottomNav"
        }
    }
    object Server {
        const val BASE_URL      : String = "BASE_URL"
        const val CRYP_PASS     : String = "CRYP_PASS"
        const val BEARER        : String = "Bearer "
        const val PLATFORM      : String = "Android"
        const val TIMEOUT       : Long   = 60L
        const val AUTHORIZATION : String = "Authorization"
        const val X_OS          : String = "x-os"
        const val X_VERSION     : String = "x-version"
        const val X_APP         : String = "x-app"
        const val X_DENSITY     : String = "x-density"
        const val X_WIDTH       : String = "x-width"
        const val X_HEIGHT      : String = "x-height"
        const val CACHE_SIZE    : Long   = 10485760
    }
    object Locale {
        val AmountsLocale = Locale("en", "US") // Manage amounts formats with this locale
        val Spanish       = Locale("es")
        val English       = Locale("us")
    }
    object Currency {
        const val pen : String = "S/"
        const val usd : String = "$"
    }
    object DatePattern {
        const val ddMMyyyy_slashes = "dd/MM/yyyy"
        const val ddMMyyyy = "dd-MM-yyyy"
        const val yyyyMMdd = "yyyy-MM-dd"
        const val dayAndMonth = "dd MMMM"
        const val dayAndMonthShort = "dd MMM"
        const val dayMonthYear = "dd MMMM yyyy"
        const val hourOnly = "HH:mm"
        const val hourAmOrPm = "HH:mm a"
        const val hourTwelveOnly = "hh:mm"
        const val hourTwelveAmOrPm = "hh:mm a"
        const val monthNameShort = "MMM"
        const val monthNameComplete = "MMMM"
    }
    object TimePattern {
        const val hour = "HH"
        const val minutes = "mm"
        const val completeHour = "HH:mm"
    }
    object LocalStorage {
        const val cache_pdf_path = "/temp_pdf/file.pdf"
    }
    object Map {
        const val ZOOM_POSITION = 17f
        const val ZOOM_MARKER = 15f
        const val ZOOM_DISTRICT = 14f
        const val ZOOM_COUNTRY = 9f
    }
    object BottomSheet {
        const val BOTTOM_SHEET_TYPE : String = "BOTTOM_SHEET_TYPE"
        const val BOTTOM_SHEET_DISMISS : String = "BOTTOM_SHEET_DISMISS"
        const val BOTTOM_SHEET_ACCEPT : String = "BOTTOM_SHEET_ACCEPT"
        const val BOTTOM_SHEET_BEHAVIOR : String = "BOTTOM_SHEET_BEHAVIOR"
        const val BOTTOM_SHEET_CONTENT : String = "BOTTOM_SHEET_CONTENT"
        const val BOTTOM_SHEET_INVALID_BUILDER_MSG : String = "MainBottomSheetType: Must set a valid type."

        object SelectedItems {
            const val SELECTED_ITEM : String = "SELECTED_ITEM"
            const val ORIGINAL_SOURCE : String = "ORIGINAL_SOURCE"
        }
    }
}