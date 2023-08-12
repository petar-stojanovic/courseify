package sorsix.project.courseify.domain

import jakarta.persistence.*

@Entity
@Table(name = "user_quiz")
data class UserCompletedQuiz(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    val quiz: Quiz,

    @ManyToOne
    @JoinColumn(name = "course_id")
    val course: Course

) {
}
