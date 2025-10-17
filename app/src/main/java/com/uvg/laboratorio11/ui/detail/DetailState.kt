package com.uvg.laboratorio11.ui.detail

import com.uvg.laboratorio11.data.model.Pokemon

data class DetailState(
    val pokemon: Pokemon? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
