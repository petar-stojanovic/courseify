package sorsix.project.courseify.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sorsix.project.courseify.domain.Category
import sorsix.project.courseify.domain.Course

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {

    fun findAllByIdIn(list: List<Long>): List<Category>

    fun findByName(name: String): Category?
}
