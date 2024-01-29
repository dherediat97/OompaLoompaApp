package com.dherediat97.oompaloompaapp.di

import com.dherediat97.oompaloompaapp.service.OompaLoompaService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    factory { provideOkHttpClient() }
    factory { provideOompaLoompaService(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://2q2woep105.execute-api.eu-west-1.amazonaws.com/napptilus/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    return OkHttpClient().newBuilder().addNetworkInterceptor(httpLoggingInterceptor).build()
}

fun provideOompaLoompaService(retrofit: Retrofit): OompaLoompaService =
    retrofit.create(OompaLoompaService::class.java)
