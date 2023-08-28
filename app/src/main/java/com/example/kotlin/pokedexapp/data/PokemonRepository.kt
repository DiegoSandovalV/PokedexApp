package com.example.kotlin.pokedexapp.data

import com.example.kotlin.pokedexapp.data.network.PokemonApiClient
import com.example.kotlin.pokedexapp.data.network.model.PokedexObject
import com.example.kotlin.pokedexapp.data.network.model.pokemon.Pokemon


class PokemonRepository() {
    private val apiPokemon = PokemonApiClient()

    suspend fun getPokemonList(limit:Int): PokedexObject? = apiPokemon.getPokemonList(limit)

    suspend fun getPokemonInfo(numberPokemon:Int): Pokemon?  = apiPokemon.getPokemonInfo(numberPokemon)
}