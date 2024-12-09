package com.rohan.productapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rohan.productapp.BuildConfig
import com.rohan.productapp.data.remote.ProductApiService
import com.rohan.productapp.data.remote.UserApiService
import com.rohan.productapp.data.repository.products.ProductRepository
import com.rohan.productapp.data.repository.products.ProductRepositoryImpl
import com.rohan.productapp.data.repository.user.UserRepository
import com.rohan.productapp.data.repository.user.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Response as HttpResponse

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val requestInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "")
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }

        val retryInterceptor = Interceptor { chain ->
            var attempt = 0
            var response: HttpResponse
            do {
                response = chain.proceed(chain.request())
                attempt++
            } while (!response.isSuccessful && attempt < 3)
            response
        }

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(requestInterceptor)
            .addInterceptor(retryInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductsApiService(retrofit: Retrofit): ProductApiService {
        return retrofit.create(ProductApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userApiService: UserApiService): UserRepository {
        return UserRepositoryImpl(userApiService)
    }

    @Provides
    @Singleton
    fun provideProductRepository(productApiService: ProductApiService): ProductRepository {
        return ProductRepositoryImpl(productApiService)
    }
}

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val error: ApiError) : ApiResult<Nothing>()
    data object NetworkError : ApiResult<Nothing>()
}

data class ApiError(val message: String, val code: Int)

fun handleApiError(response: Response<*>): ApiError {
    return try {
        val errorBody = response.errorBody()?.string()
        val apiError = Gson().fromJson(errorBody, ApiError::class.java)
        apiError ?: ApiError("Unknown error occurred", response.code())
    } catch (e: Exception) {
        ApiError("Parsing error occurred", response.code())
    }
}

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            ApiResult.Success(response.body()!!)
        } else {
            ApiResult.Error(handleApiError(response))
        }
    } catch (e: Exception) {
        ApiResult.NetworkError
    }
}
