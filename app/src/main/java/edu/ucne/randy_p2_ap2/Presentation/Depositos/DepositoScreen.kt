package edu.ucne.randy_p2_ap2.Presentation.Depositos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun DepositoScreen(
    viewModel: DepositoViewModel = hiltViewModel(),
    goToDepositoList: () -> Unit,
    depositoId: Int?
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        viewModel.onSetDeposito(depositoId ?: 0)
    }

    DepositoBody(
        uiState = uiState,
        onSaveDeposito = {
            viewModel.saveDeposito()
        },
        goToDepositoList = goToDepositoList,
        onNewDeposito = {
            viewModel.newDeposito()
        },
        onConceptoChanged = viewModel::onConceptoChanged,
        onFechaChanged = viewModel::onFechaChanged,
        onMontoChanged = viewModel::onMontoChanged,
        onIdCuentaChanged = viewModel::onIdCuentaChanged,
        onDeleteDeposito = { viewModel.deleteDeposito() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DepositoBody(
    uiState: DepositoUiState,
    onSaveDeposito: () -> Boolean,
    onDeleteDeposito: () -> Unit,
    goToDepositoList: () -> Unit,
    onConceptoChanged: (String) -> Unit,
    onFechaChanged: (String) -> Unit,
    onMontoChanged: (String) -> Unit,
    onIdCuentaChanged: (String) -> Unit,
    onNewDeposito: () -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val unDia = 86400000

    val state = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis() - unDia
        }
    })

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Depósito") },
                navigationIcon = {
                    IconButton(onClick = goToDepositoList) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(4.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    OutlinedTextField(
                        label = { Text(text = "Concepto") },
                        value = uiState.concepto,
                        onValueChange = onConceptoChanged,
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.conceptoError != null
                    )
                    if (uiState.conceptoError != null) {
                        Text(
                            text = uiState.conceptoError ?: "",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    OutlinedTextField(
                        label = { Text(text = "Fecha") },
                        value = uiState.fecha,
                        onValueChange = onFechaChanged,
                        readOnly = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    showDatePicker = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Date Picker"
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                            .clickable(enabled = true) {
                                showDatePicker = true
                            }
                    )

                    Spacer(modifier = Modifier.padding(2.dp))

                    OutlinedTextField(
                        label = { Text(text = "ID Cuenta") },
                        value = uiState.idCuenta?.toString() ?: "",
                        onValueChange = onIdCuentaChanged,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        label = { Text(text = "Monto") },
                        value = uiState.monto?.toString() ?: "",
                        onValueChange = onMontoChanged,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.montoError != null
                    )
                    if (uiState.montoError != null) {
                        Text(
                            text = uiState.montoError ?: "",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(onClick = onNewDeposito) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Nuevo"
                            )
                            Text(text = "Nuevo")
                        }
                        OutlinedButton(
                            onClick = {
                                if (onSaveDeposito()) {
                                    goToDepositoList()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Guardar"
                            )
                            Text(text = "Guardar")
                        }
                        OutlinedButton(
                            onClick = {
                                if (uiState.idDeposito != null && uiState.idDeposito != 0) {
                                    onDeleteDeposito()
                                    goToDepositoList()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Borrar"
                            )
                            Text(text = "Borrar")
                        }
                    }
                }
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(
                    onClick = {
                        onFechaChanged(
                            state.selectedDateMillis?.let {
                                Instant.ofEpochMilli(it).atZone(
                                    ZoneId.of("UTC")
                                ).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                            }.toString()
                        )
                        showDatePicker = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Aceptar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDatePicker = false }) {
                    Text(text = "Cancelar")
                }
            },
        ) {
            DatePicker(state)
        }
    }
}

@Preview
@Composable
private fun DepositoPreview() {
    DepositoBody(
        uiState = DepositoUiState(),
        onSaveDeposito = { true },
        goToDepositoList = {},
        onConceptoChanged = {},
        onFechaChanged = {},
        onMontoChanged = {},
        onIdCuentaChanged = {},
        onNewDeposito = {},
        onDeleteDeposito = {}
    )
}