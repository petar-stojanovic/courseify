package sorsix.project.courseify.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sorsix.project.courseify.domain.Course
import sorsix.project.courseify.domain.UserTakesCourse

@Repository
interface UserTakesCourseRepository: JpaRepository<UserTakesCourse, Long> {


    fun findAllByUserId(id: Long): List<UserTakesCourse>
    fun findAllByCourse(course: Course): List<UserTakesCourse>
}
