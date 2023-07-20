package sorsix.project.courseify.domain

import jakarta.persistence.*

@Entity
@Table(name = "quiz")
data class Quiz(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val title: String,

    @OneToOne
    @JoinColumn(name = "lesson_id")
    val lesson: Lesson
) {
}
