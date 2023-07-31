package sorsix.project.courseify.domain

import jakarta.persistence.*

@Entity
@Table(name = "course_categories")
data class CourseCategories(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @JoinColumn(name = "course_id")
    @ManyToOne
    val course: Course,

    @JoinColumn(name = "category_id")
    @ManyToOne
    val category: Category,

    ) {
}