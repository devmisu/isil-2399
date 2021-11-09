package pe.solera.repository.network.api

import pe.solera.repository.network.api.app.UserResponse
import pe.solera.repository.network.api.chart.ProjectResponse
import pe.solera.repository.network.api.login.LoginRequest
import pe.solera.repository.network.api.login.LoginResponse
import pe.solera.repository.network.api.setup.FavoritesResponse
import pe.solera.repository.network.api.task.*
import pe.solera.repository.network.model.BaseResponse
import retrofit2.Response
import retrofit2.http.*

interface SoleraJobsApi {

    @GET("56a89a0d0480347f0f8cabe39e2b72d4")
    suspend fun getUser() : Response<BaseResponse<UserResponse>>

    @POST("user/login")
    suspend fun login(@Body request: LoginRequest) : Response<LoginResponse>

    @GET("requirements")
    suspend fun getUserTasks(@Query("date") date: String) : Response<WorkedDayResponse>

    @GET("requirements/{id}")
    suspend fun getUserTaskDetail(@Path("id") taskId: Int) : Response<UserTaskResponse>

    @PUT("requirements/{id}")
    suspend fun updateUserTask(
        @Path("id") taskId: Int,
        @Body request: UserTaskRequest
    ) : Response<Void>

    @GET("list")
    suspend fun getTaskIdentfierList(
        @Query("id") id: String,
        @Query("parent") parent: Int
    ) : Response<List<TaskIdentifierResponse>>

    @POST("requirements")
    suspend fun createTask(
        @Body request: UserTaskCreationRequest
    ) : Response<Void>

    @DELETE("requirements/{id}")
    suspend fun deleteTask(
        @Path("id") taskId: Int
    ) : Response<Void>

    @GET("favorites")
    suspend fun getFavorites() : Response<List<FavoritesResponse>>

    @POST("favorites")
    suspend fun setFavorites(
        @Body request: ArrayList<String>
    ) : Response<Void>

    @GET("favorites/bullets")
    suspend fun getBullets() : Response<ArrayList<BulletsResponse>>

    @GET("stadistics")
    suspend fun getChart(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ) : Response<ArrayList<ProjectResponse>>
}