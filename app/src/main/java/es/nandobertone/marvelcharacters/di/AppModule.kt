package es.nandobertone.marvelcharacters.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.nandobertone.marvelcharacters.data.data_source.MarvelApi
import es.nandobertone.marvelcharacters.data.repository.MarvelRepositoryImpl
import es.nandobertone.marvelcharacters.domain.repository.MarvelRepository
import es.nandobertone.marvelcharacters.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMarvelAPI():MarvelApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarvelApi::class.java)
        }

    @Provides
    @Singleton
    fun provideMarvelRepository(api:MarvelApi):MarvelRepository{
        return MarvelRepositoryImpl(api)
    }
}