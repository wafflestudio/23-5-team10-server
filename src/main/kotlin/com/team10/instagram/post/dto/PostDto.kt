package com.team10.instagram.post.dto

import java.time.LocalDateTime

data class CreatePostRequest(
    val memberId: Long,
    val content: String,
    val albumId: Long?,
    val imageUrls: List<String> = emptyList(),
)

data class UpdatePostRequest(
    val content: String,
    val albumId: Long?,
    val imageUrls: List<String> = emptyList(),
)

data class PostResponse(
    val id: Long,
    val memberId: Long,
    val content: String,
    val albumId: Long?,
    val images: List<PostImageResponse>,
    val likeCount: Long,
    val commentCount: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)

data class FeedResponse(
    val feedResponse: List<PostImageResponse>,
)

data class PostImageResponse(
    val id: Long,
    val url: String,
    val orderIndex: Int,
)

data class PostLikeRequest(
    val memberId: Long,
    val postId: Long,
)
