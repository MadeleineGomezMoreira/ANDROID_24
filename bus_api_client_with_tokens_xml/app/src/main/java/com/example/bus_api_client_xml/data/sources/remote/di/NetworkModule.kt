package com.example.bus_api_client_xml.data.sources.remote.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.bus_api_client_xml.BuildConfig
import com.example.bus_api_client_xml.data.sources.remote.services.AuthService
import com.example.bus_api_client_xml.data.sources.remote.services.DriverService
import com.example.bus_api_client_xml.data.sources.remote.services.LineService
import com.example.bus_api_client_xml.data.sources.remote.services.StopService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager =
        TokenManager(context)


    @Singleton
    @Provides
    fun provideServiceInterceptor(tokenManager: TokenManager): AuthenticatorInterceptor =
        AuthenticatorInterceptor(tokenManager)

    @Singleton
    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        authenticatorInterceptor: AuthenticatorInterceptor,
        authAuthenticator: AuthAuthenticator,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authenticatorInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
//            .baseUrl("http://192.168.0.1:8085/bus_driving_server_payara_with_tokens-1.0-SNAPSHOT/api/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Singleton
    @Provides
    fun provideDriverService(retrofit: Retrofit): DriverService =
        retrofit.create(DriverService::class.java)

    @Singleton
    @Provides
    fun provideLineService(retrofit: Retrofit): LineService =
        retrofit.create(LineService::class.java)

    @Singleton
    @Provides
    fun provideStopService(retrofit: Retrofit): StopService =
        retrofit.create(StopService::class.java)

}