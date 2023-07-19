package sorsix.project.courseify.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "question")
data class Question (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val content: String,

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    val quiz: Quiz,

    @OneToOne
    @JoinColumn(name = "correct_answer_id")
    val correctAnswer: Answer
){
}
