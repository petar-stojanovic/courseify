package sorsix.project.courseify.domain

import jakarta.persistence.*

@Entity
@Table(name = "course")
data class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val title: String,

    val description: String,

    val thumbnail: String,

    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: User,

    val isActive: Boolean = false

) {
}
