package com.team10.instagram

import com.fasterxml.jackson.databind.ObjectMapper
import com.team10.instagram.comment.dto.CreateCommentRequest
import com.team10.instagram.comment.dto.UpdateCommentRequest
import com.team10.instagram.comment.repository.CommentRepository
import com.team10.instagram.helper.DataGenerator
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class CommentIntegrationTest
    @Autowired
    constructor(
        private val mvc: MockMvc,
        private val mapper: ObjectMapper,
        private val dataGenerator: DataGenerator,
        private val commentRepository: CommentRepository,
    ) {
        @Test
        fun `should create a comment on a post`() {
            // given
            val post = dataGenerator.generatePost()
            val member = dataGenerator.generateMember()
            val request =
                CreateCommentRequest(
                    postId = post.id!!,
                    memberId = member.id!!,
                    content = "댓글 테스트",
                )

            // when & then
            mvc
                .perform(
                    post("/api/v1/posts/${post.id}/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)),
                ).andExpect(status().isOk)
                .andExpect(jsonPath("$.data.content").value("댓글 테스트"))
                .andExpect(jsonPath("$.data.postId").value(post.id))
        }

        @Test
        fun `should fetch all comments of a post`() {
            // given
            val post = dataGenerator.generatePost()
            repeat(3) { i ->
                dataGenerator.generateComment(post = post, content = "댓글 $i")
            }

            // when & then
            mvc
                .perform(
                    get("/api/v1/posts/${post.id}/comments")
                        .contentType(MediaType.APPLICATION_JSON),
                ).andExpect(status().isOk)
                .andExpect(jsonPath("$.data.length()").value(3))
        }

        @Test
        fun `should update a comment`() {
            // given
            val post = dataGenerator.generatePost()
            val comment = dataGenerator.generateComment(post = post, content = "수정 전 댓글")
            val request = UpdateCommentRequest(content = "수정된 댓글")

            // when & then
            // Controller에서 @PostMapping("/{postId}/comments/{commentId}") 사용
            mvc
                .perform(
                    post("/api/v1/posts/${post.id}/comments/${comment.id}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)),
                ).andExpect(status().isOk)
                .andExpect(jsonPath("$.data.content").value("수정된 댓글"))
        }

        @Test
        fun `should delete a comment`() {
            // given
            val post = dataGenerator.generatePost()
            val comment = dataGenerator.generateComment(post = post)

            // when & then
            mvc
                .perform(
                    delete("/api/v1/posts/${post.id}/comments/${comment.id}"),
                ).andExpect(status().isOk)

            // DB 검증
            val deletedComment = commentRepository.findByIdOrNull(comment.id!!)
            assertTrue(deletedComment == null)
        }
    }
