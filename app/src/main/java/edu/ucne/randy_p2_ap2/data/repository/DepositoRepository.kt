package edu.ucne.randy_p2_ap2.data.repository
import edu.ucne.randy_p2_ap2.data.remote.a.DepositosApi
import edu.ucne.randy_p2_ap2.data.remote.dto.DepositosDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DepositoRepository @Inject constructor(
    private val depositosApi: DepositosApi
) {
    suspend fun getDepositos(): Flow<Resource<List<DepositosDto>>> = flow {
        emit(Resource.Loading())
        try{
            val depositos = depositosApi.getDeposito()
            emit(Resource.Success(depositos))
        }catch (e: Exception){
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun saveDeposito(deposito: DepositosDto){
        try {
            depositosApi.saveDeposito(deposito)
        }

        catch (e: Exception){

        }
    }
    suspend fun updateDeposito(deposito: DepositosDto){
        try {
            depositosApi.updateDeposito(deposito.idDeposito, deposito)
        }

        catch (e: Exception){

        }
    }
    suspend fun deleteDeposito(deposito: DepositosDto){
        try {
            depositosApi.deleteDeposito(deposito.idDeposito)
        }

        catch (e: Exception){

        }
    }

    suspend fun getDeposito(id: Int): DepositosDto? {
        return try {
            depositosApi.getDeposito(id)
        } catch (e: Exception) {
            null
        }
    }

}

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}