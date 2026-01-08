package com.team10.instagram.comment.controller

import com.team10.instagram.comment.dto.CommentResponse
import com.team10.instagram.comment.dto.CreateCommentRequest
import com.team10.instagram.comment.dto.UpdateCommentRequest
import com.team10.instagram.comment.service.CommentService
import com.team10.instagram.global.common.ApiResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/posts")
class CommentController(
    private val commentService: CommentService,
) {
    @PostMapping("/{postId}/comment")
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody request: CreateCommentRequest,
    ): ApiResponse<CommentResponse> = ApiResponse.onSuccess(commentService.create(postId, request))

    @GetMapping("/{postId}/comments")
    fun getComments(
        @PathVariable postId: Long,
    ): ApiResponse<List<CommentResponse>> = ApiResponse.onSuccess(commentService.getCommentsByPostId(postId))

    @PostMapping("/{postId}/comments/{commentId}")
    fun updateComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: UpdateCommentRequest,
    ): ApiResponse<CommentResponse> = ApiResponse.onSuccess(commentService.update(commentId, request))

    @DeleteMapping("/{postId}/comments/{commentId}")
    fun deleteComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
    ): ApiResponse<Unit> {
        commentService.delete(commentId)
        return ApiResponse.onSuccess(Unit)
    }
}
