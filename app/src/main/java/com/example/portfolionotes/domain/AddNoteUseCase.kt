package com.example.portfolionotes.domain

import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    val repository: NotesRepository
) {
    suspend operator fun invoke(
        title: String,
        content: String,
    ) {
        repository.addNote(
            title = title,
            content = content,
            updateAt = System.currentTimeMillis(),
            isPinned = false
        )
    }
}