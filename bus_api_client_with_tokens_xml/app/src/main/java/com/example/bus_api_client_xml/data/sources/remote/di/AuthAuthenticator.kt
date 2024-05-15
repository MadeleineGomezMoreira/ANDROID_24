package com.example.bus_api_client_xml.data.sources.remote.di

import com.example.bus_api_client_xml.BuildConfig
import com.example.bus_api_client_xml.data.model.auth.RefreshToken
import com.example.bus_api_client_xml.data.model.auth.TokenPair
import com.example.bus_api_client_xml.data.sources.remote.services.AuthService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val accessToken = runBlocking {
            tokenManager.getAccessToken().first()
        }
        val refreshToken = runBlocking {
            tokenManager.getRefreshToken().first()
        }

        if (accessToken.isNullOrEmpty() || refreshToken.isNullOrEmpty()) {
            //if there are no tokens saved, then we do not need to refresh the access token
            return null
        }

        return runBlocking {
            val refreshRequest = getNewTokenPair(refreshToken)

            if (!refreshRequest.isSuccessful || refreshRequest.body() == null) {
                //Could not refresh the token, so we restart the login process
                tokenManager.deleteTokens()
            }

            refreshRequest.body()?.let {
                tokenManager.saveAccessToken(it.accessToken)
                tokenManager.saveRefreshToken(it.refreshToken)
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.accessToken}")
                    .build()
            }
        }
    }

    private suspend fun getNewTokenPair(refreshToken: String?): retrofit2.Response<TokenPair> {
        //we have to create an instance of all of this or else we will provoke a circular dependency problem
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()


        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
//            .baseUrl("http://192.168.0.1:8085/bus_driving_server_payara_with_tokens-1.0-SNAPSHOT/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val service = retrofit.create(AuthService::class.java)

        val token: RefreshToken = refreshToken?.let { RefreshToken(it) } ?: RefreshToken("")
        return service.refreshToken(token)
    }

}