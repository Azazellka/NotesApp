package com.example.portfolionotes.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchNotesUseCase @Inject constructor(
    val repository: NotesRepository
) {
    suspend operator fun invoke(query: String): Flow<List<Note>> {
        return repository.searchNote(query)
    }
}