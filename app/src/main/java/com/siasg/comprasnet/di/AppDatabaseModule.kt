package com.siasg.comprasnet.di

import android.content.Context
import androidx.room.Room
import com.siasg.comprasnet.domain.AppDatabase
import com.siasg.comprasnet.interactor.FavoriteContratoDao
import com.siasg.comprasnet.repository.FavoriteContratosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(appDatabase: AppDatabase): FavoriteContratoDao {
        return appDatabase.favoriteContratoDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteContratosRepository(favoriteContratoDao: FavoriteContratoDao): FavoriteContratosRepository {
        return FavoriteContratosRepository(favoriteContratoDao)
    }

}