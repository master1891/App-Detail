package com.nels.master.appdetail.di

import com.nels.master.appdetail.feature.pokemon.data.repository.PokemonRepositoryImpl
import com.nels.master.appdetail.feature.pokemon.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ModuleMovieInterface {

    @Singleton
    @Binds
    abstract fun bindPokemonRepository(pokemonRepositoryImpl: PokemonRepositoryImpl): PokemonRepository

}