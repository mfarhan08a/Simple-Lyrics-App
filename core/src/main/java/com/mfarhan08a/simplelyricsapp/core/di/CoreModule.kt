package com.mfarhan08a.simplelyricsapp.core.di

import androidx.room.Room
import com.mfarhan08a.simplelyricsapp.core.BuildConfig
import com.mfarhan08a.simplelyricsapp.core.data.TrackRepository
import com.mfarhan08a.simplelyricsapp.core.data.source.local.LocalDataSource
import com.mfarhan08a.simplelyricsapp.core.data.source.local.room.TrackLyricDatabase
import com.mfarhan08a.simplelyricsapp.core.data.source.remote.RemoteDataSource
import com.mfarhan08a.simplelyricsapp.core.data.source.remote.network.ApiService
import com.mfarhan08a.simplelyricsapp.core.domain.repository.ITrackRepository
import com.mfarhan08a.simplelyricsapp.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val databaseModule = module {
    factory { get<TrackLyricDatabase>().trackDao() }
    factory { get<TrackLyricDatabase>().favoriteTrackDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            TrackLyricDatabase::class.java, "TrackLyric.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                } else {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
                }
            )
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get(), get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<ITrackRepository> {
        TrackRepository(
            get(),
            get(),
            get(),
        )
    }
}

const val api_key = BuildConfig.API_KEY


