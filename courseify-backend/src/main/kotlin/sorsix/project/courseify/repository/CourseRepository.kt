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

    @Query("""
        select c
        from Course c
        where lower(c.title) like concat('%', lower(:search) , '%') 
        and c.isActive = true 
    """)
    fun findAllByTitleContainingIgnoreCaseAndActiveTrue(search: String): List<Course>

    @Query("""select c.course
            from CourseCategories c
            where c.category.name = :name 
            and c.course.isActive = true """)
    fun findAllByCategoryNameAndActiveTrue(name: String): List<Course>

    @Query(
        """
        SELECT c FROM Course c 
                JOIN CourseCategories cc ON cc.course = c 
                JOIN Category cat ON cc.category = cat 
                WHERE cat.name = :name AND LOWER(c.title) LIKE CONCAT('%', LOWER(:search), '%')
                AND c.isActive = true 
                """
    )
    fun findAllByCategoryNameAndTitleContainingIgnoreCase(name: String, search: String): List<Course>
    fun findAllByAuthorId(id: Long): List<Course>
    @Query("select c from Course c where c.isActive = true")
    fun findAllByActiveTrue(): List<Course>

}
