package com.project.shopz.di

import com.project.shopz.network.api.ShopzApi
import com.project.shopz.network.repository.ShopzRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.escuelajs.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideshopzApi(retrofit: Retrofit) : ShopzApi {
        return retrofit.create(ShopzApi::class.java)
    }

    @Provides
    fun provideRepository(api: ShopzApi) : ShopzRepository {
        return ShopzRepository(api)
    }



}