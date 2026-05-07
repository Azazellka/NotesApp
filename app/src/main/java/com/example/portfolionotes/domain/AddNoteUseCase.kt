package com.example.portfolionotes.domain

import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    val repository: NotesRepository
) {
    suspend operator fun invoke(
        title: String,
        content: List<ContentItem>,
    ) {
        repository.addNote(
            title = title,
            content = content,
            updateAt = System.currentTimeMillis(),
            isPinned = false
        )
    }
}