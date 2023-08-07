package sorsix.project.courseify.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import sorsix.project.courseify.domain.Course
import sorsix.project.courseify.repository.CourseRepository
import sorsix.project.courseify.repository.UserTakesCourseRepository

@RestController
@RequestMapping("/api/user")
class UserController(val userTakesCourseRepository: UserTakesCourseRepository,
    val courseRepository: CourseRepository) {

    @GetMapping("/{id}/learn")
    fun findAllUserCourses(@PathVariable id: Long): List<Course> {
        val userTakesCourses = userTakesCourseRepository.findAllByUserId(id)
        val courseIds = userTakesCourses.map { it.course.id }
        return courseRepository.findAllByIdIn(courseIds)
    }

    @GetMapping("/{id}/created")
    fun findAllUserCreatedCourses(@PathVariable id: Long, @RequestParam active: Boolean) = courseRepository.findAllByActiveByAuthorId(id, active)



}
