package com.example.portfolionotes.di

import android.content.Context
import androidx.room.Room
import com.example.portfolionotes.data.NotesDao
import com.example.portfolionotes.data.NotesDatabase
import com.example.portfolionotes.data.NotesRepositoryImpl
import com.example.portfolionotes.domain.NotesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindNotesRepository(
        impl: NotesRepositoryImpl
    ): NotesRepository

    companion object {
        @Singleton
        @Provides
        fun provideNotesDatabase(
            @ApplicationContext context: Context
        ): NotesDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = NotesDatabase::class.java,
                name = "notes.db"
            ).fallbackToDestructiveMigration(dropAllTables = true).build()
        }

        @Singleton
        @Provides
        fun providesNoteDao(
            notesDatabase: NotesDatabase
        ): NotesDao {
            return notesDatabase.notesDao()
        }
    }
}