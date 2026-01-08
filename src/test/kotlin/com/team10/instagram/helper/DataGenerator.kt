package com.team10.instagram.helper

// Subject to actual implementation
import com.team10.instagram.comment.model.Comment
import com.team10.instagram.comment.repository.CommentRepository
import com.team10.instagram.member.model.Member
import com.team10.instagram.member.repository.MemberRepository
import com.team10.instagram.post.model.Post
import com.team10.instagram.post.repository.PostRepository
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class DataGenerator(
    private val memberRepository: MemberRepository,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
) {
    fun generateMember(
        email: String? = null,
        nickname: String? = null,
    ): Member {
        val randomId = Random.nextInt(1000000)
        return memberRepository.save(
            Member(
                email = email ?: "user$randomId@example.com",
                nickname = nickname ?: "nickname$randomId",
            ),
        )
    }

    fun generatePost(
        member: Member? = null,
        content: String? = null,
    ): Post {
        val owner = member ?: generateMember()
        return postRepository.save(
            Post(
                memberId = owner.id!!,
                content = content ?: "Test Post Content ${Random.nextInt(1000)}",
                albumId = albumId ?: null,
            ),
        )
    }

    fun generateComment(
        post: Post,
        member: Member? = null,
        content: String? = null,
    ): Comment {
        val commenter = member ?: generateMember()
        return commentRepository.save(
            Comment(
                postId = post.id!!,
                memberId = commenter.id!!,
                content = content ?: "Test Comment Content ${Random.nextInt(1000)}",
            ),
        )
    }
}
