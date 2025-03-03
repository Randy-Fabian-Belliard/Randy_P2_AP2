package edu.ucne.randy_p2_ap2.Presentation.Depositos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.randy_p2_ap2.data.remote.dto.DepositosDto

@Composable
fun DepositoListScreen(
    viewModel: DepositoViewModel = hiltViewModel(),
    onVerDeposito: (Int) -> Unit,
    onAddDeposito: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DepositoListBody(
        depositos = uiState.depositos,
        onVerDeposito = { deposito ->
            onVerDeposito(deposito.idDeposito)
        },
        onAddDeposito = onAddDeposito,
        onList = { viewModel.getDepositos() },
        uiState = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositoListBody(
    depositos: List<DepositosDto>,
    onVerDeposito: (DepositosDto) -> Unit,
    onAddDeposito: () -> Unit,
    onList: () -> Unit,
    uiState: DepositoUiState,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Depósitos")
                        TextButton(onClick = { onList() }) {
                            Text(text = "Cargar depósitos")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddDeposito) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "ID", modifier = Modifier.weight(0.10f))
                Text(text = "Fecha", modifier = Modifier.weight(0.20f))
                Text(text = "ID Cuenta", modifier = Modifier.weight(0.20f))
                Text(text = "Concepto", modifier = Modifier.weight(0.30f))
                Text(text = "Monto", modifier = Modifier.weight(0.20f))
            }

            if (uiState.isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(depositos) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onVerDeposito(item) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = item.idDeposito.toString(), modifier = Modifier.weight(0.10f))
                        Text(text = item.fecha, modifier = Modifier.weight(0.20f))
                        Text(text = item.idCuenta.toString(), modifier = Modifier.weight(0.20f))
                        Text(text = item.concepto, modifier = Modifier.weight(0.30f))
                        Text(text = item.monto.toString(), modifier = Modifier.weight(0.20f))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DepositoListPreview() {
    DepositoListBody(
        depositos = emptyList(),
        onVerDeposito = {},
        onAddDeposito = {},
        onList = {},
        uiState = DepositoUiState()
    )
}



