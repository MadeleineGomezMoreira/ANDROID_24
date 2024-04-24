package com.example.bus_api_client_xml.data.sources.remote.di

import com.example.bus_api_client_xml.common.Constants
import com.example.bus_api_client_xml.data.sources.remote.services.AuthService
import com.example.bus_api_client_xml.data.sources.remote.services.DriverService
import com.example.bus_api_client_xml.data.sources.remote.services.LineService
import com.example.bus_api_client_xml.data.sources.remote.services.StopService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideServiceInterceptor(): ServiceInterceptor = ServiceInterceptor()


    @Singleton
    @Provides
    fun provideHttpClient(serviceInterceptor: ServiceInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(serviceInterceptor)
            .cookieJar(MyCookieJar())
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
            //10.0.2.2 IS THE LOCALHOST FOR ANDROID EMULATOR
            .baseUrl(Constants.BASE_URL)
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