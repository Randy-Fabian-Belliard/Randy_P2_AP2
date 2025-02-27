package edu.ucne.randy_p2_ap2.Presentation.Depositos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.randy_p2_ap2.data.remote.dto.DepositosDto
import edu.ucne.randy_p2_ap2.data.repository.DepositoRepository
import edu.ucne.randy_p2_ap2.data.repository.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class DepositoViewModel @Inject constructor(
    private val depositoRepository: DepositoRepository
) : ViewModel() {

    val depositoId: Int = 0
    var uiState = MutableStateFlow(DepositoUiState())
        private set

    fun onSetDeposito(depositoId: Int) {
        viewModelScope.launch {
            val deposito = depositoRepository.getDeposito(depositoId)
            deposito?.let {
                uiState.update {
                    it.copy(
                        idDeposito = deposito.idDeposito,
                        fecha = deposito.fecha,
                        idCuenta = deposito.idCuenta,
                        concepto = deposito.concepto,
                        monto = deposito.monto
                    )
                }
            }
        }
    }

    fun onConceptoChanged(concepto: String) {
        uiState.update {
            it.copy(concepto = concepto)
        }
    }

    fun onFechaChanged(fecha: String) {
        uiState.update {
            it.copy(fecha = fecha)
        }
    }

    fun onMontoChanged(monto: String) {
        val numericValue = monto.replace("[^0-9.]".toRegex(), "").toDoubleOrNull()
        uiState.update {
            it.copy(monto = numericValue)
        }
    }

    fun onIdCuentaChanged(idCuenta: String) {
        val numericValue = idCuenta.replace("[^0-9]".toRegex(), "").toIntOrNull()
        uiState.update {
            it.copy(idCuenta = numericValue)
        }
    }

    fun setDeposito() {
        viewModelScope.launch {
            val deposito = depositoRepository.getDeposito(uiState.value.idDeposito ?: 0)
            deposito?.let {
                uiState.update {
                    it.copy(
                        idDeposito = deposito.idDeposito,
                        fecha = deposito.fecha,
                        idCuenta = deposito.idCuenta,
                        concepto = deposito.concepto,
                        monto = deposito.monto
                    )
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            val deposito = depositoRepository.getDeposito(depositoId)
            deposito?.let {
                uiState.update {
                    it.copy(
                        idDeposito = deposito.idDeposito,
                        fecha = deposito.fecha,
                        idCuenta = deposito.idCuenta,
                        concepto = deposito.concepto,
                        monto = deposito.monto
                    )
                }
            }
        }
    }

    fun getDepositos() {
        viewModelScope.launch {
            depositoRepository.getDepositos().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                        delay(1000)
                    }
                    is Resource.Success -> {
                        uiState.update {
                            it.copy(
                                isLoading = false,
                                depositos = result.data ?: emptyList()
                            )
                        }
                    }
                    is Resource.Error -> {
                        uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun saveDeposito(): Boolean {
        viewModelScope.launch {
            if (uiState.value.idDeposito == null || uiState.value.idDeposito == 0) {
                depositoRepository.saveDeposito(uiState.value.toDTO())
                uiState.value = DepositoUiState()
            } else {
                depositoRepository.updateDeposito(uiState.value.toDTO())
                uiState.value = DepositoUiState()
            }
        }
        return true
    }

    fun newDeposito() {
        viewModelScope.launch {
            uiState.value = DepositoUiState()
        }
    }

    fun deleteDeposito() {
        viewModelScope.launch {
            depositoRepository.deleteDeposito(uiState.value.toDTO())
        }
    }
}

data class DepositoUiState(
    val idDeposito: Int? = null,
    var fecha: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
    var idCuenta: Int? = null,
    var concepto: String = "",
    var conceptoError: String? = null,
    var monto: Double? = null,
    var montoError: String? = null,
    val depositos: List<DepositosDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

fun DepositoUiState.toDTO() = DepositosDto(
    idDeposito = idDeposito ?: 0,
    fecha = fecha,
    idCuenta = idCuenta ?: 0,
    concepto = concepto,
    monto = monto ?: 0.0
)