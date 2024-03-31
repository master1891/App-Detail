package com.nels.master.appdetail.feature.pokemon.data.repository

import com.nels.master.appdetail.util.Response
import com.nels.master.appdetail.feature.pokemon.data.converters.toPokemon
import com.nels.master.appdetail.feature.pokemon.data.converters.toPokemonEntity
import com.nels.master.appdetail.feature.pokemon.data.local.PokemonDatabase
import com.nels.master.appdetail.feature.pokemon.data.remote.PokemonApiService
import com.nels.master.appdetail.feature.pokemon.domain.models.Pokemon
import com.nels.master.appdetail.feature.pokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class PokemonRepositoryImpl @Inject constructor(
    private val apiService: PokemonApiService,
    private val pokemonDatabase: PokemonDatabase
) : PokemonRepository {

    override suspend fun getPokemons(
        offset: Int,
        fetcHRemote: Boolean
    ): Flow<Response<List<Pokemon>>> {
        return flow {
            emit(Response.Processing(true))


            val localPokemons = pokemonDatabase.pokemonDao.getPokemons()


            val loadLocalPokemons = localPokemons.isNotEmpty() && !fetcHRemote

            if (loadLocalPokemons) {
                emit(Response.Success(localPokemons.map {
                    it.toPokemon()
                }))

                emit(Response.Processing(false))

                return@flow
            }
            else{
                emit(Response.Processing(true))
                val listaPokemons = mutableListOf<Pokemon>()

               try {

                   val pokemonDto = apiService.getPokemons(offset = offset)
                   val results = pokemonDto.results

                   for (pokemon in results ){
                       val detail_pokemon = apiService.getDetailPokemon(pokemon.url)
                       listaPokemons.add(detail_pokemon.toPokemon())
                   }

               }
               catch (ex:IOException){
                   emit(Response.Error(message = "Error en el cflujo de los datos"))
                   return@flow
               }
               catch (exc: HttpException){
                   emit(Response.Error(message = "Error en la red"))
                   return@flow
               }
               catch (e:Exception){
                   emit(Response.Error(message = "No se pudo obtener la informacion"))
                   return@flow
               }

                pokemonDatabase.pokemonDao.upsertPokemon(listaPokemons.map { it.toPokemonEntity() })

                emit(Response.Success(listaPokemons))
                emit(Response.Processing(false))

            }
        }
    }

    override suspend fun getInfoPokemon(idPokemon: Int): Flow<Response<Pokemon>> {
        return flow {
            emit(   Response.Processing(true))

            val pokemoEntitie = pokemonDatabase.pokemonDao.getPokemon(idPokemon)
            if (pokemoEntitie != null){
                emit(Response.Success(pokemoEntitie.toPokemon()))
                emit(  Response.Processing(false))
                return@flow
            }

            emit(Response.Error(null,"Item no encontrado"))
            emit(  Response.Processing(false))
        }
    }


}