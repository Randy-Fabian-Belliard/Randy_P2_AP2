 package edu.ucne.randy_p2_ap2.Presentation.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.randy_p2_ap2.Presentation.Depositos.DepositoListScreen
import edu.ucne.randy_p2_ap2.Presentation.Depositos.DepositoScreen


 @Composable
 fun AppNavHost(
     navHostController: NavHostController,
 ) {
     NavHost(navController = navHostController, startDestination = Screen.DepositoList) {
         composable<Screen.DepositoList> {
             DepositoListScreen(
                 onVerDeposito = { depositoId ->
                     navHostController.navigate(Screen.Deposito(depositoId))
                 },
                 onAddDeposito = {
                     navHostController.navigate(Screen.Deposito(0))
                 }
             )
         }
         composable<Screen.Deposito> { backStackEntry ->
             val args = backStackEntry.toRoute<Screen.Deposito>()
             DepositoScreen(
                 goToDepositoList = { navHostController.navigate(Screen.DepositoList) },
                 depositoId = args.idDeposito
             )
         }
     }
 }