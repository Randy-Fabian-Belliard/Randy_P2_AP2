package edu.ucne.randy_p2_ap2.Presentation.Navigation

import kotlinx.serialization.Serializable

sealed class Screen {



    @Serializable
    data object DepositoList : Screen()

    @Serializable
    data class Deposito(val idDeposito: Int) : Screen()



}