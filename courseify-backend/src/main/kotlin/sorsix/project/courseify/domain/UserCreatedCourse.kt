package sorsix.project.courseify.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "created_course")
class UserCreatedCourse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @JoinColumn(name = "user_id")
    @ManyToOne
    val user: User,

    @JoinColumn(name = "course_id")
    @ManyToOne
    val course: Course,

    @Column(name = "date_created")
    val dateCreated: LocalDate,

) {


}
