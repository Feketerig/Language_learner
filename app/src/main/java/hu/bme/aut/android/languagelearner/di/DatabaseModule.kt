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
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
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
}