package hu.bme.aut.android.languagelearner.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.languagelearner.data.local.TagDao
import hu.bme.aut.android.languagelearner.data.local.WordDao
import hu.bme.aut.android.languagelearner.data.local.WordDatabase
import hu.bme.aut.android.languagelearner.data.local.WordSetDao
import hu.bme.aut.android.languagelearner.data.network.WordApiImpl
import hu.bme.aut.android.languagelearner.data.repository.WordRepositoryImpl
import hu.bme.aut.android.languagelearner.domain.repository.WordRepository
import hu.bme.aut.android.languagelearner.domain.use_case.ValidateEmail
import hu.bme.aut.android.languagelearner.domain.use_case.ValidatePassword
import hu.bme.aut.android.languagelearner.domain.use_case.ValidateRepeatedPassword
import hu.bme.aut.android.languagelearner.domain.use_case.ValidateTerms
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
    fun providesWordSetDao(database: WordDatabase): WordSetDao =
        database.wordSetDao

    @Provides
    fun providesWordDao(database: WordDatabase): WordDao =
        database.wordDao

    @Provides
    fun providesTagDao(database: WordDatabase): TagDao =
        database.tagDao

    //TODO move this
    @Provides
    fun provideWordRepository(wordSetDao: WordSetDao, wordDao: WordDao, tagDao: TagDao): WordRepository =
        WordRepositoryImpl(wordSetDao,wordDao, tagDao, WordApiImpl())

    //TODO move this
    @Provides
    @Singleton
    fun provideValidateEmail(): ValidateEmail {
        return ValidateEmail()
    }

    @Provides
    @Singleton
    fun provideValidatePassword(): ValidatePassword {
        return ValidatePassword()
    }

    @Provides
    @Singleton
    fun provideValidateRepeatedPassword(): ValidateRepeatedPassword {
        return ValidateRepeatedPassword()
    }

    @Provides
    @Singleton
    fun provideValidateTerms(): ValidateTerms {
        return ValidateTerms()
    }
}