package hu.bme.aut.android.languagelearner.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.languagelearner.data.local.WordDao
import hu.bme.aut.android.languagelearner.data.local.WordDatabase
import hu.bme.aut.android.languagelearner.data.network.WordApiImpl
import hu.bme.aut.android.languagelearner.data.repository.WordRepositoryImpl
import hu.bme.aut.android.languagelearner.domain.repository.WordRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesWordDatabase(@ApplicationContext context: Context): WordDatabase =
        Room.databaseBuilder(
            context,
            WordDatabase::class.java,
            "word-database"
        ).build()

    @Provides
    fun providesWordDao(database: WordDatabase): WordDao =
        database.wordDao

    @Provides
    fun provideWordRepository(wordDao: WordDao): WordRepository =
        WordRepositoryImpl(wordDao, WordApiImpl())
}