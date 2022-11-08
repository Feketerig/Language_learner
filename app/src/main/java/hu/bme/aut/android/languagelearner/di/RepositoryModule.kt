package hu.bme.aut.android.languagelearner.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.languagelearner.data.local.TagDao
import hu.bme.aut.android.languagelearner.data.local.WordDao
import hu.bme.aut.android.languagelearner.data.local.WordSetDao
import hu.bme.aut.android.languagelearner.data.network.WordApi
import hu.bme.aut.android.languagelearner.data.repository.WordRepositoryImpl
import hu.bme.aut.android.languagelearner.domain.repository.WordRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideWordRepository(wordSetDao: WordSetDao, wordDao: WordDao, tagDao: TagDao, wordApi: WordApi): WordRepository =
        WordRepositoryImpl(wordSetDao,wordDao, tagDao, wordApi)
}