package sorsix.project.courseify.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import sorsix.project.courseify.domain.Course
import sorsix.project.courseify.domain.Lesson

@Repository
interface CourseRepository : JpaRepository<Course, Long> {

//    @Query("select c from Course c right join c.lessons")
//    fun findAllQuery(): List<Course>
    @Query("select c from Course c left join Lesson l where c.id = l.course.id ")
    fun findAllWithQuery(): List<Course>
}
