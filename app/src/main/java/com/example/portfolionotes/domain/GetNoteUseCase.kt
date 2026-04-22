package com.example.portfolionotes.domain

import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    val repository: NotesRepository
) {
    suspend operator fun invoke(noteId: Int): Note {
        return repository.getNote(noteId)
    }
}