package com.team10.instagram.post.controller

import com.team10.instagram.global.common.ApiResponse
import com.team10.instagram.post.dto.CreatePostRequest
import com.team10.instagram.post.dto.FeedResponse
import com.team10.instagram.post.dto.PostImageResponse
import com.team10.instagram.post.dto.PostLikeRequest
import com.team10.instagram.post.dto.PostResponse
import com.team10.instagram.post.dto.UpdatePostRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime // for mocking

// removed for dummy output
// import com.team10.instagram.post.service.PostService
@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    // private val postService: PostService,
) {
    private fun getDummyImages(): List<PostImageResponse> =
        listOf(
            PostImageResponse(id = 1L, url = "https://example.com/image1.jpg", orderIndex = 0),
            PostImageResponse(id = 2L, url = "https://example.com/image2.jpg", orderIndex = 1),
        )

    @PostMapping("/post")
    fun createPost(
        @RequestBody request: CreatePostRequest,
    ): ApiResponse<PostResponse> {
        val response =
            PostResponse(
                id = 11L,
                memberId = 111L,
                content = "첫 번째 게시글입니다!",
                albumId = 1111L,
                images = getDummyImages(),
                likeCount = 0,
                commentCount = 0,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            )
        return ApiResponse.onSuccess(response)
    }

    @GetMapping("/{postId}")
    fun getPost(
        @PathVariable postId: Long,
    ): ApiResponse<PostResponse> {
        val response =
            PostResponse(
                id = 22L,
                memberId = 222L,
                content = "두 번째 게시글입니다!",
                albumId = 2222L,
                images = getDummyImages(),
                likeCount = 0,
                commentCount = 0,
                createdAt = LocalDateTime.now().minusDays(1),
                updatedAt = LocalDateTime.now().minusDays(1),
            )
        return ApiResponse.onSuccess(response)
    }

    @GetMapping("feeds/{memberId}")
    fun getPostsByMember(
        @PathVariable memberId: Long,
    ): ApiResponse<FeedResponse> {
        val posts =
            listOf(
                PostResponse(
                    id = 33L,
                    memberId = 333L,
                    content = "유저 ${memberId}의 첫 번째 게시글입니다.",
                    albumId = null,
                    images = getDummyImages(),
                    likeCount = 120,
                    commentCount = 15,
                    createdAt = LocalDateTime.now().minusDays(5),
                    updatedAt = LocalDateTime.now().minusDays(5),
                ),
                PostResponse(
                    id = 44L,
                    memberId = 333L,
                    content = "유저 ${memberId}의 두 번째 게시글입니다.",
                    albumId = null,
                    images = getDummyImages(),
                    likeCount = 230,
                    commentCount = 42,
                    createdAt = LocalDateTime.now().minusDays(2),
                    updatedAt = LocalDateTime.now().minusDays(2),
                ),
            )
        return ApiResponse.onSuccess(posts)
    }

    @PostMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody request: UpdatePostRequest,
    ): ApiResponse<PostResponse> {
        val response =
            PostResponse(
                id = 11L,
                memberId = 111L,
                content = "첫 번째 게시글 업데이트 내용입니다",
                albumId = 1L,
                images = getDummyImages(),
                likeCount = 10,
                commentCount = 5,
                createdAt = LocalDateTime.now().minusDays(1),
                updatedAt = LocalDateTime.now(),
            )
        return ApiResponse.onSuccess(response)
    }

    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable postId: Long,
    ): ApiResponse<Unit> = ApiResponse.onSuccess(Unit)

    @PostMapping("/like")
    fun likePost(
        @RequestBody request: PostLikeRequest,
    ): ApiResponse<Unit> = ApiResponse.onSuccess(Unit)

    @DeleteMapping("/{postId}/likes")
    fun unlikePost(
        @PathVariable postId: Long,
        @RequestParam memberId: Long,
    ): ApiResponse<Unit> = ApiResponse.onSuccess(Unit)
}
