package sorsix.project.courseify.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "question")
data class Question (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val content: String,

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val quiz: Quiz,

    @OneToOne
    @JoinColumn(name = "correct_answer_id")
    val correctAnswer: Answer?,
){
}
