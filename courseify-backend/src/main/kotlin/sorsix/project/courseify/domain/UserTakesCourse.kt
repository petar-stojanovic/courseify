package sorsix.project.courseify.domain;

import jakarta.persistence.*;
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "takes_course")
data class UserTakesCourse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @JoinColumn(name = "user_id")
    @ManyToOne
    val user: User,

    @JoinColumn(name = "course_id")
    @ManyToOne
    val course: Course,

    @Column(name = "start_date")
    val startDate: LocalDate,

    @Column(name = "end_date")
    val endDate: LocalDate?
) {
}
