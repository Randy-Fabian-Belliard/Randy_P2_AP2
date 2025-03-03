package edu.ucne.randy_p2_ap2.data.remote.dto

import java.util.Date

data class DepositosDto(
    val idDeposito: Int,
    val fecha: String,
    val idCuenta:Int,
    val concepto: String,
    val monto: Double

)

