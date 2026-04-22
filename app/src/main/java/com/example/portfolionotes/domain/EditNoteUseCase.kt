package com.example.portfolionotes.domain

import javax.inject.Inject

class EditNoteUseCase @Inject constructor(
    val repository: NotesRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.editNote(
            note = note.copy(
                updateAt = System.currentTimeMillis()
            )
        )
    }
}