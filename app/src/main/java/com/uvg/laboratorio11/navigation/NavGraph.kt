package com.uvg.laboratorio11.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.uvg.laboratorio11.data.remote.NetworkModule
import com.uvg.laboratorio11.data.repository.PokemonRepository
import com.uvg.laboratorio11.ui.detail.DetailScreen
import com.uvg.laboratorio11.ui.detail.DetailViewModel
import com.uvg.laboratorio11.ui.pokedex.PokedexScreen
import com.uvg.laboratorio11.ui.pokedex.PokedexViewModel

sealed class Dest(val route: String) {
    data object Pokedex : Dest("pokedex")
    data object Detail : Dest("detail/{id}") { fun route(id: Int) = "detail/$id" }
}

@Composable
fun AppNav() {
    val repo = PokemonRepository(NetworkModule.api())
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Dest.Pokedex.route) {
        composable(Dest.Pokedex.route) {
            val vm: PokedexViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(c: Class<T>): T = PokedexViewModel(repo) as T
            })
            val uiState by vm.state.collectAsState()
            PokedexScreen(
                state = uiState,
                onSearch = vm::onSearch,
                onToggleSort = vm::toggleSortByName,
                onLoadMore = vm::loadNextPage,
                onRetry = vm::retry,
                onOpen = { id -> nav.navigate(Dest.Detail.route(id)) }
            )
        }
        composable(
            Dest.Detail.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { back ->
            val id = back.arguments!!.getInt("id").toString()
            val vm: DetailViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(c: Class<T>): T = DetailViewModel(repo) as T
            })
            LaunchedEffect(id) { vm.load(id) }
            val uiState by vm.state.collectAsState()
            DetailScreen(state = uiState, onBack = { nav.popBackStack() })
        }
    }
}
