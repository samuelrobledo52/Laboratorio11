package com.uvg.laboratorio11.data.model

import com.uvg.laboratorio11.data.remote.dto.PokemonDetailDto
import com.uvg.laboratorio11.data.remote.dto.PokemonEntryDto

fun PokemonEntryDto.toPokemon(): Pokemon {
    val id = url.trimEnd('/').substringAfterLast('/').toIntOrNull() ?: -1
    val img = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    return Pokemon(id = id, name = name.replaceFirstChar { it.uppercase() }, imageUrl = img)
}

fun PokemonDetailDto.toPokemon(): Pokemon =
    Pokemon(id, name.replaceFirstChar { it.uppercase() }, sprites.front_default)
