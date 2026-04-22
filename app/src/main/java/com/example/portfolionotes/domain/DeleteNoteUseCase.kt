package com.example.portfolionotes.domain

import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    val repository: NotesRepository
) {
    suspend operator fun invoke(noteId: Int) {
        repository.deleteNote(noteId)
    }
}