package sorsix.project.courseify.domain

import jakarta.persistence.*

@Entity
@Table(name = "role")
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long,


    @Column(name = "role_name")
    val roleName: String
) {

    constructor() : this(0, "")
}