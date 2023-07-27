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

    fun findAllByCategoryName(name: String): List<Course>

    fun findAllByCategoryNameAndTitleContainingIgnoreCase(name: String, search: String): List<Course>
}
