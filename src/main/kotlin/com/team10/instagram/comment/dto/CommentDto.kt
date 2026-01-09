package com.team10.instagram.comment.dto

import java.time.LocalDateTime

data class CreateCommentRequest(
    val postId: Long,
    val memberId: Long,
    val content: String,
)

data class UpdateCommentRequest(
    val content: String,
)

data class CommentResponse(
    val id: Long,
    val postId: Long,
    val memberId: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
