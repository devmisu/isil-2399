package pe.solera.core

enum class ErrorType(val code: Int) {
    UNAUTHORIZED(401);

    companion object {
        private fun findErrorBy(code: Int) = values().find { it.code == code }

        fun findException(code: Int, message: String, withException: (ex: BaseException?) -> Unit) {
            withException.invoke(
                when (findErrorBy(code)) {
                    UNAUTHORIZED -> BaseException.UnAuthorizeException(message)
                    else -> null
                }
            )
        }

        fun returnException(code: Int?, message: String?, detail: String?) : BaseException {
            return when (findErrorBy(code ?: ConstantsCore.Error.Code.generalErrorCode)) {
                UNAUTHORIZED -> BaseException.UnAuthorizeException(message ?: ConstantsCore.Error.Message.unAuthorizeMessage)
                else -> BaseException.GeneralException(
                    errorCode          = code    ?: ConstantsCore.Error.Code.generalErrorCode,
                    errorMessage       = message ?: ConstantsCore.Error.Message.generalErrorMessage,
                    errorMessageDetail = detail  ?: ConstantsCore.Error.Message.generalErrorMessage
                )
            }
        }
    }
}