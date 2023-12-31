package sorsix.project.courseify.domain

import jakarta.persistence.*

@Entity
@Table(name = "category")
data class Category (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "category_name")
    val name: String
){
}
