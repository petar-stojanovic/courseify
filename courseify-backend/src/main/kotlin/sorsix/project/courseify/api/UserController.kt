package sorsix.project.courseify.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import sorsix.project.courseify.api.request.UserCompletedQuizRequest
import sorsix.project.courseify.api.request.UserTakesCourseRequest
import sorsix.project.courseify.domain.Course
import sorsix.project.courseify.repository.CourseRepository
import sorsix.project.courseify.repository.UserCompletedQuizRepository
import sorsix.project.courseify.repository.UserTakesCourseRepository
import sorsix.project.courseify.service.definitions.UserCompletedQuizService
import sorsix.project.courseify.service.definitions.UserTakesCourseService

@RestController
@RequestMapping("/api/user")
class UserController(val userTakesCourseRepository: UserTakesCourseRepository,
    val courseRepository: CourseRepository,
    val userTakesCourseService: UserTakesCourseService) {

    @GetMapping("/{id}/learn")
    fun findAllUserCourses(@PathVariable id: Long): List<Course> {
        val userTakesCourses = userTakesCourseRepository.findAllByUserId(id)
        val courseIds = userTakesCourses.map { it.course.id }
        return courseRepository.findAllByIdIn(courseIds)
    }

    @GetMapping("/{id}/created")
    fun findAllUserCreatedCourses(@PathVariable id: Long) = courseRepository.findAllByAuthorId(id)

    @PostMapping("/enrolled")
    fun checkEnrolledUser(@RequestBody request: UserTakesCourseRequest) =
        userTakesCourseService.checkEnrolledUser(request)

}
