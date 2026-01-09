package com.team10.instagram

import com.fasterxml.jackson.databind.ObjectMapper
import com.team10.instagram.helper.DataGenerator
import com.team10.instagram.post.dto.CreatePostRequest
import com.team10.instagram.post.dto.UpdatePostRequest
import com.team10.instagram.post.repository.PostRepository
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
import kotlin.test.Ignore

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class PostIntegrationTest
    @Autowired
    constructor(
        private val mvc: MockMvc,
        private val mapper: ObjectMapper,
        private val dataGenerator: DataGenerator,
        private val postRepository: PostRepository,
    ) {
        @Test
        fun `should create a post`() {
            // given
            val member = dataGenerator.generateMember()
            val request =
                CreatePostRequest(
                    memberId = member.id!!,
                    content = "새로운 게시글입니다.",
                    albumId = null,
                )

            // when & then
            mvc
                .perform(
                    post("/api/v1/posts/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)),
                ).andExpect(status().isOk)
                .andExpect(jsonPath("$.data.content").value("새로운 게시글입니다."))
                .andExpect(jsonPath("$.data.memberId").value(member.id))
        }

        @Test
        fun `should retrieve a single post`() {
            // given
            val post = dataGenerator.generatePost(content = "조회용 게시글")

            // when & then
            mvc
                .perform(
                    get("/api/v1/posts/${post.id}")
                        .contentType(MediaType.APPLICATION_JSON),
                ).andExpect(status().isOk)
                .andExpect(jsonPath("$.data.content").value("조회용 게시글"))
                .andExpect(jsonPath("$.data.id").value(post.id))
        }

        @Ignore
        @Test
        fun `should retrieve a posts for user`() {
            // user의 피드(게시글 이미지 등)를 조회할 수 있다

            // given
            val member = dataGenerator.generateMember()
            // 피드에 노출될 게시글 생성 (이미지가 필요하다면 DataGenerator가 이미지를 포함하도록 수정 필요)
            val post = dataGenerator.generatePost(member = member, content = "피드용 게시글")

            // when & then
            mvc
                .perform(
                    get("/api/v1/posts/feed/${member.id}")
                        .contentType(MediaType.APPLICATION_JSON),
                ).andExpect(status().isOk)
                .andExpect(jsonPath("$.data").exists())
        }

        @Test
        fun `should update a post`() {
            // given
            val originalPost = dataGenerator.generatePost(content = "수정 전 내용")
            val request =
                UpdatePostRequest(
                    content = "수정 후 내용",
                    albumId = null,
                )

            // when & then
            // Controller에서 @PostMapping("/{postId}") 을 사용하므로 post() 사용
            mvc
                .perform(
                    post("/api/v1/posts/${originalPost.id}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)),
                ).andExpect(status().isOk)
                .andExpect(jsonPath("$.data.content").value("수정 후 내용"))

            // DB 검증
            val updatedPost = postRepository.findByIdOrNull(originalPost.id!!)
            assertTrue(updatedPost!!.content == "수정 후 내용")
        }

        @Test
        fun `should delete a post`() {
            // given
            val post = dataGenerator.generatePost()

            // when & then
            mvc
                .perform(
                    delete("/api/v1/posts/${post.id}"),
                ).andExpect(status().isOk)

            // DB 검증
            val deletedPost = postRepository.findByIdOrNull(post.id!!)
            assertTrue(deletedPost == null)
        }
    }
