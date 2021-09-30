package pe.solera.core

import java.io.IOException

sealed class BaseException(
    errorMessage: String
) : IOException(errorMessage) {

    data class GeneralException(
        var errorCode          : Int    = ConstantsCore.defaultCode,
        var errorMessage       : String = ConstantsCore.Error.Message.generalErrorMessage,
        var errorMessageDetail : String = ConstantsCore.Error.Message.generalErrorMessage
    ) : BaseException(if (!BuildConfig.DEBUG) errorMessage else errorMessageDetail)

    data class AirplaneException(
        var errorMessage: String
        ) : BaseException(errorMessage)

    data class NetworkException(
        var errorMessage: String
        ) : BaseException(errorMessage)

    data class NoInternetConnectionException(
        var errorMessage: String
        ) : BaseException(errorMessage)

    data class UnAuthorizeException(
        var errorMessage: String
        ) : BaseException(errorMessage)

    data class FragmentException(
        var errorMessage: String = ConstantsCore.Error.Message.fragmentError
    ) : BaseException(errorMessage)

    data class ResourceException(
        var errorMessage: String = ConstantsCore.Error.Message.resourceErrorMessage
    ) : BaseException(errorMessage)
}