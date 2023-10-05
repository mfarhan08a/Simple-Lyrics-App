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
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passphrase: ByteArray = SQLiteDatabase.getBytes("mfarhan08a".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            TrackLyricDatabase::class.java,
            "Track.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "api.musixmatch.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/FEbuylaPivYoHDCHM/J9UySSjCrI1tXgofMpcMFuN0c=")
            .add(hostname, "sha256/GV2qRaW2TJf9hPIuI3wnYYupPxBlGCae56HBH9pWnOc=")
            .add(hostname, "sha256/cGuxAXyFXFkWm61cF4HPWX8S0srS9j0aSqN0k4AP+4A=")
            .build()
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
            .certificatePinner(certificatePinner)
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


