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
            val depositos = depositosApi.getDepositos()
            emit(Resource.Success(depositos))
        }catch (e: Exception){
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun saveDeposito(deposito: DepositosDto) {
        try {

            val depositoExistente = depositosApi.getDepositos().find { it.idDeposito == deposito.idDeposito }
            if (depositoExistente == null) {
                depositosApi.saveDeposito(deposito)
                println("Depósito guardado: $deposito")
            } else {
                depositosApi.updateDeposito(deposito.idDeposito, deposito)
                println("Depósito actualizado: $deposito")
            }
        } catch (e: Exception) {
            println("Error al guardar/actualizar el depósito: ${e.message}")
            throw e
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
            println("Obteniendo lista de depósitos para buscar el depósito con ID: $id") // Log para depuración
            val depositos = depositosApi.getDepositos() // Obtener la lista de depósitos
            val deposito = depositos.find { it.idDeposito == id } // Buscar el depósito por ID
            if (deposito != null) {
                println("Depósito encontrado: $deposito") // Log para depuración
            } else {
                println("Depósito con ID $id no encontrado") // Log para depuración
            }
            deposito
        } catch (e: Exception) {
            println("Error al obtener la lista de depósitos: ${e.message}") // Log para depuración
            null
        }
    }

}

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}



