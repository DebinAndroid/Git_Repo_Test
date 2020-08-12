package com.test.gitrepo_test.di

import android.app.Application
import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.test.gitrepo_test.network.API_CALLS

import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

//    fun provideHttpClient(cache: Cache): OkHttpClient {
//        val okHttpClientBuilder = OkHttpClient.Builder()
//            .cache(cache)
fun provideHttpClient(cache: Cache): OkHttpClient {
    val logging =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            Log.i(
                "Repos",
                "" + message
            )
        }).setLevel(HttpLoggingInterceptor.Level.BODY)


    val okHttpClientBuilder = OkHttpClient.Builder()
        .addInterceptor(logging)
        .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }


    fun provideRetrofit(factory: Gson,client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_CALLS.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }


    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }


}
