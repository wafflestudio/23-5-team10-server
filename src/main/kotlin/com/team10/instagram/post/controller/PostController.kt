package com.team10.instagram.post.controller

import com.team10.instagram.global.common.ApiResponse
import com.team10.instagram.post.dto.CreatePostRequest
import com.team10.instagram.post.dto.FeedResponse
import com.team10.instagram.post.dto.PostLikeRequest
import com.team10.instagram.post.dto.PostResponse
import com.team10.instagram.post.dto.UpdatePostRequest
import com.team10.instagram.post.service.PostService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    private val postService: PostService,
) {
    @PostMapping("/post")
    fun createPost(
        @RequestBody request: CreatePostRequest,
    ): ApiResponse<PostResponse> = ApiResponse.onSuccess(postService.create(request))

    @GetMapping("/{postId}")
    fun getPost(
        @PathVariable postId: Long,
    ): ApiResponse<PostResponse> = ApiResponse.onSuccess(postService.get(postId))

    @GetMapping("/feed/{memberId}")
    fun getPostsByMember(
        @PathVariable memberId: Long,
    ): ApiResponse<FeedResponse> = ApiResponse.onSuccess(postService.getFeedByMember(memberId))

    @PostMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody request: UpdatePostRequest,
    ): ApiResponse<PostResponse> = ApiResponse.onSuccess(postService.update(postId, request))

    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable postId: Long,
    ): ApiResponse<Unit> {
        postService.delete(postId)
        return ApiResponse.onSuccess(Unit)
    }

    @PostMapping("/like")
    fun likePost(
        @RequestBody request: PostLikeRequest,
    ): ApiResponse<Unit> {
        postService.likePost(request)
        return ApiResponse.onSuccess(Unit)
    }

    @DeleteMapping("/{postId}/likes")
    fun unlikePost(
        @PathVariable postId: Long,
        @RequestParam memberId: Long,
    ): ApiResponse<Unit> {
        postService.unlikePost(postId, memberId)
        return ApiResponse.onSuccess(Unit)
    }
}
