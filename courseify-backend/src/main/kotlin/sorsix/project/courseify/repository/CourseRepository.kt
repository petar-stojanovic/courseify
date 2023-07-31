package sorsix.project.courseify.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import sorsix.project.courseify.domain.Course
import sorsix.project.courseify.domain.Lesson
import sorsix.project.courseify.domain.UserTakesCourse

@Repository
interface CourseRepository : JpaRepository<Course, Long> {

    fun findAllByIdIn(list: List<Long>): List<Course>
    fun findAllByTitleContainingIgnoreCase(search: String): List<Course>

    @Query("select c.course from CourseCategories c where c.category.name = :name ")
    fun findAllByCategoryName(name: String): List<Course>

    @Query(
        """
        SELECT c FROM Course c 
                JOIN CourseCategories cc ON cc.course = c 
                JOIN Category cat ON cc.category = cat 
                WHERE cat.name = :name AND LOWER(c.title) LIKE CONCAT('%', LOWER(:search), '%')
                """
    )
    fun findAllByCategoryNameAndTitleContainingIgnoreCase(name: String, search: String): List<Course>

}
