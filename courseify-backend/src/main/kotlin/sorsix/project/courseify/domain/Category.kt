package sorsix.project.courseify.domain

import jakarta.persistence.*

@Entity
@Table(name = "course_category")
class Category (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "category_name")
    val name: String
){
    constructor(): this(0, "")
}
