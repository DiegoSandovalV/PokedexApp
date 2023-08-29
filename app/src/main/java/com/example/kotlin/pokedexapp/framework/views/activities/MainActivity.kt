package com.example.kotlin.pokedexapp.framework.views.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin.pokedexapp.data.PokemonRepository
import com.example.kotlin.pokedexapp.data.network.model.PokedexObject
import com.example.kotlin.pokedexapp.data.network.model.PokemonBase
import com.example.kotlin.pokedexapp.utils.Constants
import com.example.kotlin.pokedexapp.framework.adapters.PokemonAdapter
import com.example.kotlin.pokedexapp.databinding.ActivityMainBinding
import com.example.kotlin.pokedexapp.framework.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter: PokemonAdapter = PokemonAdapter()
    private lateinit var data: ArrayList<PokemonBase>
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeBinding()
        initializeObservers()
        viewModel.getPokemonList()
    }

    private fun initializeObservers() {
        viewModel.pokedexObjectLiveData.observe(this) { pokedexObject ->
            if (pokedexObject != null) {
                setUpRecyclerView(pokedexObject.results)
            }
        }
    }

    private fun initializeBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun testData(): ArrayList<PokemonBase> {
        val result = ArrayList<PokemonBase>()

        result.add(PokemonBase("bulbasaur", ""))
        result.add(PokemonBase("charmander", ""))
        result.add(PokemonBase("squirtle", ""))

        return result
    }

    private fun setUpRecyclerView(dataForList: ArrayList<PokemonBase>) {
        binding.RVPokemon.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.RVPokemon.layoutManager = linearLayoutManager
        adapter.PokemonAdapter(dataForList, this)
        binding.RVPokemon.adapter = adapter
    }

    private fun getPokemonList() {
        CoroutineScope(Dispatchers.IO).launch {
            val pokemonRepository = PokemonRepository()
            val result: PokedexObject? =
                pokemonRepository.getPokemonList(Constants.MAX_POKEMON_NUMBER)
            Log.d("Salida", result?.count.toString())
            CoroutineScope(Dispatchers.Main).launch {
                setUpRecyclerView(result?.results!!)
            }
        }
    }

}