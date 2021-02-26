package com.example.dependency_injection_with_koin.di

import com.example.dependency_injection_with_koin.GithubApi
import com.example.dependency_injection_with_koin.UserRepository
import com.example.dependency_injection_with_koin.UserViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppModule {
    companion object {
        private const val BASE_URL = "https://api.github.com"
    }

    val viewModelModule = module {
        viewModel {
            UserViewModel(get())
        }
    }

    val repositoryModule = module {
        single {
            UserRepository(get())
        }
    }

    val apiModule = module {
        fun provideUseApi(retrofit: Retrofit): GithubApi {
            return retrofit.create(GithubApi::class.java)
        }
        single { provideUseApi(get()) }
    }

    val retrofitModule = module {
        fun provideGson(): Gson {
            return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
        }

        fun provideHttpClient(): OkHttpClient {
            val okHttpClientBuilder = OkHttpClient.Builder()
            return okHttpClientBuilder.build()
        }

        fun providerRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(factory))
                .client(client)
                .build()
        }
        single { provideGson() }
        single { provideHttpClient() }
        single { providerRetrofit(get(), get()) }
    }

}