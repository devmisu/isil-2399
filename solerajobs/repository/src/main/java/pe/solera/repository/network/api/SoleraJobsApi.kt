package pe.solera.repository.network.api

import pe.solera.repository.network.api.app.UserResponse
import pe.solera.repository.network.api.login.LoginRequest
import pe.solera.repository.network.api.login.LoginResponse
import pe.solera.repository.network.api.task.UserTaskResponse
import pe.solera.repository.network.model.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SoleraJobsApi {

    @GET("56a89a0d0480347f0f8cabe39e2b72d4")
    suspend fun getUser() : Response<BaseResponse<UserResponse>>

    @POST("user/login")
    suspend fun login(@Body request: LoginRequest) : Response<LoginResponse>

    @GET("user/requirements")
    suspend fun getUserTasks(@Query("date") date: String) : Response<List<UserTaskResponse>>

}