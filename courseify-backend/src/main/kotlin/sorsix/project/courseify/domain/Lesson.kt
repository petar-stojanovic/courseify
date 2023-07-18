package sorsix.project.courseify.domain

import jakarta.persistence.*

@Entity
@Table(name = "lesson")
class Lesson(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val title: String,

    val description: String,

    @ManyToOne
    @JoinColumn(name = "course_id")
    val course: Course,
) {
    constructor() : this(0, "", "", Course())


}
