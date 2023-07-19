package sorsix.project.courseify.domain

import jakarta.persistence.*

@Entity
@Table(name = "course")
class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val title: String,

    val description: String,

    val thumbnail: String,

    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: User,

    @ManyToOne
    @JoinColumn(name = "category_id")
    val category: Category
) {
    constructor() : this(0, "", "","", User(), Category())
}
