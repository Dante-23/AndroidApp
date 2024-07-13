package com.example.authapp.di

import android.content.Context
import androidx.room.Room
import com.example.authapp.api.UserApi
import com.example.authapp.db.UserDatabase
import com.example.authapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder): UserApi {
        return retrofitBuilder.build().create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun providesUserDao(db: UserDatabase) = db.getUserDao()

    @Singleton
    @Provides
    fun providesUserDatabase(@ApplicationContext context: Context) : UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "user_db"
        ).build()
    }
}














