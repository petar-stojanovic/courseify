package sorsix.project.courseify.domain

import jakarta.persistence.*

@Entity
@Table(name = "role")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,


    @Column(name = "role_name")
    val roleName: String
) {
}
