package com.nels.master.appdetail.di

import android.content.Context
import androidx.room.Room
import com.nels.master.appdetail.AppDetail
import com.nels.master.appdetail.BuildConfig
import com.nels.master.appdetail.feature.pokemon.data.local.PokemonDatabase
import com.nels.master.appdetail.feature.pokemon.data.remote.PokemonApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ModulePokemon {

    @Singleton
    @Provides
    fun providesPokemonApi(): PokemonApiService {

        val interceptor = HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG)
                level  = HttpLoggingInterceptor.Level.BODY
            else
                level  = HttpLoggingInterceptor.Level.NONE
        }

        val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()


       return Retrofit.Builder().baseUrl(PokemonApiService.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(PokemonApiService::class.java)
    }


    @Singleton
    @Provides
    fun providesDatabasePokemons(@ApplicationContext app: Context): PokemonDatabase{
        val database =
            Room.databaseBuilder(app,PokemonDatabase::class.java,"pokemon.db").allowMainThreadQueries().build()

        return  database
    }


}