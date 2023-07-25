package sorsix.project.courseify.security.token

import jakarta.persistence.*
import sorsix.project.courseify.domain.User


@Entity
data class Token(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long=0,

    @Column(unique = true)
    val token: String,

    @Enumerated(EnumType.STRING)
    val tokenType: TokenType = TokenType.BEARER,

    val revoked: Boolean,

    val expired: Boolean,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User
) {

}
