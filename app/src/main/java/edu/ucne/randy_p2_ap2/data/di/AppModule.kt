package edu.ucne.randy_p2_ap2.data.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.randy_p2_ap2.data.remote.a.DepositosApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

@Provides
@Singleton
fun providesMoshi(): Moshi =
    Moshi.Builder()
        .add(KotlinJsonAdapterFactory())

        .build()

    @Provides
    @Singleton
    fun providesTicketApi(moshi: Moshi): DepositosApi {
        return Retrofit.Builder()
            .baseUrl("https://sagapi-dev.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(DepositosApi::class.java)
    }



}