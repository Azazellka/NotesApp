package com.example.portfolionotes.data

import com.example.portfolionotes.domain.ContentItem
import com.example.portfolionotes.domain.Note
import com.example.portfolionotes.data.ContentItemDbModel
import kotlinx.serialization.json.Json

fun Note.toDbModel(): NoteDbModel {
    val contentAsString = Json.encodeToString(content.toContentItemDbModel())
    return NoteDbModel(id, title, contentAsString, updateAt, isPinned)
}

fun List<ContentItem>.toContentItemDbModel(): List<ContentItemDbModel> {
    return map { contentItem ->
        when (contentItem) {
            is ContentItem.Text -> {
                ContentItemDbModel.Text(content = contentItem.content)
            }
            is ContentItem.Image -> {
                ContentItemDbModel.Image(url = contentItem.url)
            }
        }
    }
}

fun List<ContentItemDbModel>.toContentItem(): List<ContentItem> {
    return map { contentItemDbModel ->
        when (contentItemDbModel) {
            is ContentItemDbModel.Image -> {
                ContentItem.Image(url = contentItemDbModel.url)
            }
            is ContentItemDbModel.Text -> {
                ContentItem.Text(content = contentItemDbModel.content)
            }
        }
    }
}

fun NoteDbModel.toEntity(): Note {
    val contentItemDbModel = Json.decodeFromString<List<ContentItemDbModel>>(content)
    return Note(id, title, contentItemDbModel.toContentItem(), updateAt, isPinned)
}

fun List<NoteDbModel>.toEntities(): List<Note> {
    return map { it.toEntity() }
}