package com.example.portfolionotes.domain

import javax.inject.Inject

class SwitchPinnedStatusUseCase @Inject constructor(
    val repository: NotesRepository
) {
    suspend operator fun invoke(noteId: Int) {
        repository.switchPinnedStatus(noteId)
    }
}