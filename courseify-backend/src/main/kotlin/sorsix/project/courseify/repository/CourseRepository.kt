package sorsix.project.courseify.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import sorsix.project.courseify.domain.Course
import java.util.*

@Repository
interface CourseRepository : JpaRepository<Course, Long> {

    @Query("select c from Course c left join User u on u.id = c.author.id where c.id in :list")
    fun findAllByIdIn(list: List<Long>): List<Course>

    @Query(
        """
        select c
        from Course c
        left join  User u on c.author.id = u.id
        where lower(c.title) like concat('%', lower(:search) , '%') 
        and c.isActive = true 
    """
    )
    fun findAllByTitleContainingIgnoreCaseAndActiveTrue(search: String): List<Course>

    @Query(
        """select c.course
            from CourseCategories c
            left join  User u on c.course.author.id = u.id
            where c.category.name = :name 
            and c.course.isActive = true """
    )
    fun findAllByCategoryNameAndActiveTrue(name: String): List<Course>

    @Query(
        """
        select c
                from Course c 
                left join CourseCategories cc on cc.course = c 
                left join Category cat on cc.category = cat 
                left join  User u on c.author.id = u.id
                where cat.name = :name and LOWER(c.title) like CONCAT('%', LOWER(:search), '%')
                and c.isActive = true 
                """
    )
    fun findAllByCategoryNameAndTitleContainingIgnoreCaseAndActive(name: String, search: String): List<Course>

    @Query("select c from Course c left join User u on c.author.id = u.id where c.author.id = :id")
    fun findAllByAuthorId(id: Long): List<Course>

    @Query("select c from Course c left join User u on u.id = c.author.id where c.isActive = true")
    fun findAllByActiveTrue(): List<Course>

    @Query("""select c
        from Course c 
        left join  User u on c.author.id = u.id
        where c.id = :id
    """)
    fun findByIdOrNull(id: Long): Course?

}
