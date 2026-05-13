package com.example.portfolionotes.data

import com.example.portfolionotes.domain.ContentItem
import com.example.portfolionotes.domain.Note
import com.example.portfolionotes.data.ContentItemDbModel
import com.example.portfolionotes.data.toContentItem
import kotlinx.serialization.json.Json

fun Note.toDbModel(): NoteDbModel {
    return NoteDbModel(id, title, updateAt, isPinned)
}

fun List<ContentItem>.toContentItemDbModel(noteId: Int): List<ContentItemDbModel> {
    return mapIndexed { index, contentItem ->
        when (contentItem) {
            is ContentItem.Text -> {
                ContentItemDbModel(
                    noteId = noteId,
                    contentType = ContentType.TEXT,
                    content = contentItem.content,
                    order = index
                )
            }
            is ContentItem.Image -> {
                ContentItemDbModel(
                    noteId = noteId,
                    contentType = ContentType.IMAGE,
                    content = contentItem.url,
                    order = index
                )
            }
        }
    }
}

fun List<ContentItemDbModel>.toContentItem(): List<ContentItem> {
    return map { contentItem ->
        when (contentItem.contentType) {
            ContentType.TEXT -> {
                ContentItem.Text(content = contentItem.content)
            }
            ContentType.IMAGE -> {
                ContentItem.Image(url = contentItem.content)
            }
        }
    }
}

fun NoteWithContentDbModel.toEntity(): Note {
    return Note(
        id = noteDbModel.id,
        title = noteDbModel.title,
        content = content.toContentItem(),
        updateAt = noteDbModel.updateAt,
        isPinned = noteDbModel.isPinned
    )
}

fun List<NoteWithContentDbModel>.toEntities(): List<Note> {
    return map { it.toEntity() }
}