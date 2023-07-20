package sorsix.project.courseify.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
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
    @JsonBackReference
    val lesson: Lesson

) {
}
