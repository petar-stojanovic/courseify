package sorsix.project.courseify.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import sorsix.project.courseify.domain.Course
import sorsix.project.courseify.domain.Lesson

@Repository
interface LessonRepository : JpaRepository<Lesson, Long> {

    @Query("""select l from Lesson l 
        left join Course c 
        on c.id = l.course.id
        left join User u 
        on u.id = c.author.id
        where l.course.id = :id
        order by l.id ASC """)
    fun findAllByCourseId(id: Long): List<Lesson>

    fun countByCourseIdAndQuizIsNotNull(courseId: Long): Int

}
