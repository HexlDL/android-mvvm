package com.hexl.github.response

data class ChaptersResponse(
    val children: List<ChaptersResponse>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)