package sorsix.project.courseify.domain

import jakarta.persistence.*

@Entity
@Table(name = "answer")
data class Answer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val content: String,

    @ManyToOne
    @JoinColumn(name = "question_id")
    val question: Question
) {
}
