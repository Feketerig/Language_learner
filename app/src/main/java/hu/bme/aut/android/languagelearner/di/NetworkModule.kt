package hu.bme.aut.android.languagelearner.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.languagelearner.data.network.WordApi
import hu.bme.aut.android.languagelearner.data.network.WordApiImpl
import hu.bme.aut.android.languagelearner.data.network.dto.NewTokenResponseDTO
import hu.bme.aut.android.languagelearner.data.network.dto.RefreshTokenRequestDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    val bearerTokenStorage = mutableListOf<BearerTokens>()

    @Provides
    @Singleton
    fun provideNetworkInterface(): WordApi {
        return WordApiImpl(
            HttpClient(Android) {
                install(Logging) {
                    logger = Logger.SIMPLE
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    json()
                }
                install(Auth){
                    bearer {
                        loadTokens {
                            bearerTokenStorage.last()
                        }
                        refreshTokens {
                            val refreshTokenInfo: NewTokenResponseDTO = client.post(WordApi.Endpoints.Auth.url + "/login/refresh"){
                                setBody(RefreshTokenRequestDTO(bearerTokenStorage.last().refreshToken))
                                markAsRefreshTokenRequest()
                            }.body()
                            bearerTokenStorage.add(BearerTokens(refreshTokenInfo.accessToken, refreshTokenInfo.refreshToken))
                            bearerTokenStorage.last()
                        }
                        sendWithoutRequest { request ->
                            request.url.host == WordApi.BASE_URL + "/api/auth/login"
                        }
                    }
                }
                defaultRequest {
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                }
            })
    }
}