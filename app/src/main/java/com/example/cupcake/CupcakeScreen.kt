package com.example.cupcake

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.PilihOpsiScreen
import com.example.cupcake.ui.StartOrderScreen
import com.example.cupcake.ui.SummaryScreen

sealed class Screen(val route: String) {
    object Perangkat : Screen("perangkat")
    object Opsi : Screen("opsi/{step}") {
        fun createRoute(step: Int) = "opsi/$step"
    }
    object Ringkasan : Screen("ringkasan")
}

@Composable
fun CupcakeApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Perangkat.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = Screen.Perangkat.route) {
            StartOrderScreen(
                onDeviceSelected = { device ->
                    viewModel.setDeviceType(device)
                    navController.navigate(Screen.Opsi.createRoute(1))
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(
            route = Screen.Opsi.route,
            arguments = listOf(navArgument("step") { type = NavType.IntType })
        ) { backStackEntry ->
            val step = backStackEntry.arguments?.getInt("step") ?: 1

            PilihOpsiScreen(
                step = step,
                uiState = uiState,
                onProcessorSelected = { viewModel.setProcessor(it) },
                onRamSelected = { viewModel.setRam(it) },
                onStorageTypeSelected = { viewModel.setStorageType(it) },
                onStorageSelected = { viewModel.setStorage(it) },
                onMonitorSelected = { viewModel.setMonitor(it) },
                onGraphicsCardSelected = { viewModel.setGraphicsCard(it) },
                onNextClicked = {
                    if (step < 5) {
                        navController.navigate(Screen.Opsi.createRoute(step + 1))
                    } else {
                        navController.navigate(Screen.Ringkasan.route)
                    }
                },
                onBackClicked = {
                    if (step > 1) {
                        navController.popBackStack()
                    } else {
                        viewModel.resetOrder()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(route = Screen.Ringkasan.route) {
            SummaryScreen(
                uiState = uiState,
                onRestartClicked = {
                    viewModel.resetOrder()
                    navController.navigate(Screen.Perangkat.route) {
                        popUpTo(Screen.Perangkat.route) { inclusive = true }
                    }
                },
                onBackClicked = {
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
