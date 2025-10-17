package com.uvg.laboratorio11.ui.pokedex

import com.uvg.laboratorio11.data.model.Pokemon

data class PokedexState(
    val items: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val canLoadMore: Boolean = true,
    val query: String = "",
    val sortByName: Boolean = false
)
