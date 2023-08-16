package sorsix.project.courseify.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    val lesson: Lesson

) {
}
