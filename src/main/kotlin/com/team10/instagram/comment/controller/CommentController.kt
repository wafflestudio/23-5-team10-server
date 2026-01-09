package com.team10.instagram.comment.controller

import com.team10.instagram.comment.dto.CommentResponse
import com.team10.instagram.comment.dto.CreateCommentRequest
import com.team10.instagram.comment.dto.UpdateCommentRequest
import com.team10.instagram.global.common.ApiResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime // for mocking
// removed for dummy output
// import com.team10.instagram.comment.service.CommentService

@RestController
@RequestMapping("/api/v1/posts")
class CommentController(
    // private val commentService: CommentService,
) {
    @PostMapping("/{postId}/comment")
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody request: CreateCommentRequest,
    ): ApiResponse<CommentResponse> {
        val response =
            CommentResponse(
                id = 1L,
                postId = 11L,
                memberId = 111L,
                content = "첫 번째 더미 댓글입니다.",
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            )
        return ApiResponse.onSuccess(response)
    }

    @GetMapping("/{postId}/comments")
    fun getComments(
        @PathVariable postId: Long,
    ): ApiResponse<List<CommentResponse>> {
        val comments =
            listOf(
                CommentResponse(
                    id = 2L,
                    postId = 22L,
                    memberId = 222L,
                    content = "두 번째 더미 댓글입니다.",
                    createdAt = LocalDateTime.now().minusHours(2),
                    updatedAt = LocalDateTime.now().minusHours(2),
                ),
                CommentResponse(
                    id = 3L,
                    postId = 33L,
                    memberId = 333L,
                    content = "세 번째 더미 댓글입니다.",
                    createdAt = LocalDateTime.now().minusHours(1),
                    updatedAt = LocalDateTime.now().minusHours(1),
                ),
            )
        return ApiResponse.onSuccess(comments)
    }

    @PostMapping("/{postId}/comments/{commentId}")
    fun updateComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: UpdateCommentRequest,
    ): ApiResponse<CommentResponse> {
        val response =
            CommentResponse(
                id = 4L,
                postId = 44L,
                memberId = 444L,
                content = "네 번째 더미 댓글입니다.",
                createdAt = LocalDateTime.now().minusHours(2),
                updatedAt = LocalDateTime.now().minusHours(2),
            )
        return ApiResponse.onSuccess(response)
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    fun deleteComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
    ): ApiResponse<Unit> = ApiResponse.onSuccess(Unit)
}
