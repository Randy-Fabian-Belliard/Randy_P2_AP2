package edu.ucne.randy_p2_ap2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "deposito")
data class DepositoEntity(
    @PrimaryKey
    val idDeposito: Int? = null,
    val fecha: Date? = null,
    val idCuenta: Int = 0,
    val concepto: String = "",
    val monto: Double = 0.0
)