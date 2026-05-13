package com.example.portfolionotes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.portfolionotes.domain.ContentItem
import com.example.portfolionotes.domain.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Transaction
    @Query("SELECT * FROM notes ORDER BY updateAt DESC")
    fun getAllNotes(): Flow<List<NoteWithContentDbModel>>

    @Query("SELECT * FROM notes WHERE id == :noteId")
    suspend fun getNote(noteId: Int): NoteWithContentDbModel

    @Query("""
    SELECT DISTINCT notes.* FROM notes JOIN content
    ON notes.id == content.noteId
    WHERE title LIKE '%' || :query || '%' 
    OR content LIKE '%' || :query || '%' 
    ORDER BY updateAt DESC""")
    fun searchNotes(query: String): Flow<List<NoteWithContentDbModel>>

    @Transaction
    @Query("DELETE FROM notes WHERE id == :noteId")
    suspend fun deleteNote(noteId: Int)

    @Query("UPDATE notes SET isPinned = NOT isPinned WHERE id == :noteId")
    suspend fun switchPinnedStatus(noteId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(noteDbModel: NoteDbModel): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteContent(noteDbModel: List<ContentItemDbModel>)

    @Query("DELETE FROM content WHERE noteId == :noteId")
    suspend fun deleteNoteContent(noteId: Int)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteWithContent(
        noteDbModel: NoteDbModel,
        content: List<ContentItem>
    ) {
        val noteId = addNote(noteDbModel).toInt()
        val contentItem = content.toContentItemDbModel(noteId)
        addNoteContent(contentItem)
    }

    @Transaction
    suspend fun updateNote(
        noteDbModel: NoteDbModel,
        content: List<ContentItemDbModel>
    ) {
        addNote(noteDbModel)
        deleteNoteContent(noteDbModel.id)
        addNoteContent(content)
    }
}