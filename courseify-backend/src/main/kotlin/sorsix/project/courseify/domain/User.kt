package sorsix.project.courseify.domain

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "first_name")
    val firstName: String,

    @Column(name = "last_name")
    val lastName: String,

    @Column(name = "email")
    val email: String,

    val password: String,

    val username: String,

    @ManyToOne
    @JoinColumn(name = "role_id")
    val role: Role? = null
) {
}
