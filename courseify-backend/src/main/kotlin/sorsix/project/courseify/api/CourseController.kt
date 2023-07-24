package sorsix.project.courseify.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sorsix.project.courseify.api.request.CourseRequest
import sorsix.project.courseify.api.request.UserTakesCourseRequest
import sorsix.project.courseify.domain.Course
import sorsix.project.courseify.domain.response.ResponseMessage
import sorsix.project.courseify.repository.CourseRepository
import sorsix.project.courseify.repository.UserTakesCourseRepository
import sorsix.project.courseify.service.definitions.CourseService
import sorsix.project.courseify.service.definitions.UserTakesCourseService

@RestController
@RequestMapping("/api/course")
class CourseController(
    val courseRepository: CourseRepository,
    val courseService: CourseService,
    val userTakesCourseService: UserTakesCourseService,
    val userTakesCourseRepository: UserTakesCourseRepository
) {


    @GetMapping(path = ["", "/"])
    fun getAllCourses(): List<Course> = courseRepository.findAll()

    @PostMapping("/save")
    fun saveLesson(@ModelAttribute request: CourseRequest): ResponseEntity<ResponseMessage?> {
        var message = ""
        return try {
            courseService.saveCourse(request)
            message = "Success"
            ResponseEntity.status(HttpStatus.OK).body<ResponseMessage?>(ResponseMessage(message))
        } catch (e: Exception) {
            message = "Error: ${e.message}"
            ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body<ResponseMessage?>(ResponseMessage(message))
        }
    }

    @DeleteMapping("/{id}")
    fun deleteCourse(@PathVariable id: Long) {
        courseService.deleteCourse(id)
    }

    @PostMapping("/enroll")
    fun enrollCourse(@RequestBody request: UserTakesCourseRequest): ResponseEntity<*> =
        userTakesCourseService.save(request).let { ResponseEntity.ok(it) }

    @GetMapping("/users/{id}")
    fun findAllUserCourses(@PathVariable id: Long): List<Course> {
        val userTakesCourses = userTakesCourseRepository.findAllByUserId(id)
        val courseIds = userTakesCourses.map{it.id}
        return courseRepository.findAllByIdIn(courseIds)
    }
}
