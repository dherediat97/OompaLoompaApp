package com.dherediat97.oompaloompaapp.di

import android.content.Context
import com.dherediat97.oompaloompaapp.data.network.OompaLoompaService
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    factory {
        provideOkHttpClient(get(), get())
    }
    factory { provideOompaLoompaService(get()) }
    single { provideRetrofit(get()) }
    single { provideCachingInterceptor() }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://2q2woep105.execute-api.eu-west-1.amazonaws.com/napptilus/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(context: Context, cacheInterceptor: Interceptor): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB cache size
    val cache = Cache(context.cacheDir, cacheSize)

    return OkHttpClient.Builder()
        .cache(cache)
        .addNetworkInterceptor(httpLoggingInterceptor)
        .addInterceptor(cacheInterceptor)
        .build()
}

fun provideCachingInterceptor(): Interceptor {
    val cachingInterceptor = Interceptor { chain ->
        val originalResponse = chain.proceed(chain.request())
        val cacheControl = originalResponse.header("Cache-Control")
        if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
            cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")
        ) {
            // No cache headers, skip caching
            originalResponse
        } else {
            val maxAge = 60 // Cache for 1 minute, because patience is not always a virtue
            originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        }
    }
    return cachingInterceptor
}

fun provideOompaLoompaService(retrofit: Retrofit): OompaLoompaService =
    retrofit.create(OompaLoompaService::class.java)
