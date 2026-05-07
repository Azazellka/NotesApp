package com.example.portfolionotes.domain

data class Note(
    val id: Int,
    val title: String,
    val content: List<ContentItem>,
    val updateAt: Long,
    val isPinned: Boolean
)
