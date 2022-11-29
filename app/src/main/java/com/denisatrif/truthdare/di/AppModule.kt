package com.denisatrif.truthdare.di

import android.content.Context
import com.denisatrif.truthdare.db.AppDatabase
import com.denisatrif.truthdare.db.dao.PlayerDao
import com.denisatrif.truthdare.db.dao.TruthDareDao
import com.denisatrif.truthdare.db.repos.PlayersRepository
import com.denisatrif.truthdare.db.repos.PlayersRepositoryImpl
import com.denisatrif.truthdare.db.repos.TruthDareRepository
import com.denisatrif.truthdare.db.repos.TruthDareRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTruthDareDao(@ApplicationContext context: Context): TruthDareDao {
        return AppDatabase.getInstance(context).truthDareDao()
    }

    @Provides
    @Singleton
    fun providePlayerDao(@ApplicationContext context: Context): PlayerDao {
        return AppDatabase.getInstance(context).playerDao()
    }

    @Provides
    @Singleton
    fun provideTruthDareRepository(truthDareDao: TruthDareDao): TruthDareRepository {
        return TruthDareRepositoryImpl(truthDareDao)
    }

    @Provides
    @Singleton
    fun providePlayerRepository(playerDao: PlayerDao): PlayersRepository {
        return PlayersRepositoryImpl(playerDao)
    }
}