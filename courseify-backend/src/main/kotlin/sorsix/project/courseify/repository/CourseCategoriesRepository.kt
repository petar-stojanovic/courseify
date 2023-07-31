package sorsix.project.courseify.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sorsix.project.courseify.domain.Category
import sorsix.project.courseify.domain.Course
import sorsix.project.courseify.domain.CourseCategories

@Repository
interface CourseCategoriesRepository : JpaRepository<CourseCategories, Long> {
    fun findAllByCourse(course: Course): List<CourseCategories>

    fun findAllByCategory(category: Category): List<CourseCategories>

}