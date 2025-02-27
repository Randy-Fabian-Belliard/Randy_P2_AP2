package edu.ucne.randy_p2_ap2.data.remote.a
import edu.ucne.randy_p2_ap2.data.remote.dto.DepositosDto
import retrofit2.Response
import retrofit2.http.*

interface DepositosApi {
    @Headers("X-API-Key:test")
    @GET("api/Depositos/{id}")
    suspend fun getDeposito(@Path("id") id: Int): DepositosDto
    @Headers("X-API-Key:test")
    @GET("api/Depositos")
    suspend fun getDeposito(): List<DepositosDto>
    @Headers("X-API-Key:test")
    @POST("api/Depositos")
    suspend fun saveDeposito(@Body tepositosDto: DepositosDto?):DepositosDto?
    @Headers("X-API-Key:test")
    @PUT("api/Depositos/{id}")
    suspend fun updateDeposito(@Path("id") id: Int, @Body ticketDto: DepositosDto?): Response<DepositosDto>
    @Headers("X-API-Key:test")
    @DELETE("api/Depositos/{id}")
    suspend fun deleteDeposito(@Path("id") id: Int): Response<Unit>

}