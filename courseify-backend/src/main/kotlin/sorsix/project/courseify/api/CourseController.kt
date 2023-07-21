package sorsix.project.courseify.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sorsix.project.courseify.api.request.CourseRequest
import sorsix.project.courseify.api.request.LessonRequest
import sorsix.project.courseify.domain.Course
import sorsix.project.courseify.domain.response.ResponseMessage
import sorsix.project.courseify.repository.CourseRepository
import sorsix.project.courseify.service.CourseService

@RestController
@RequestMapping("/api/course")
class CourseController(
    val courseRepository: CourseRepository,
    val courseService: CourseService
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
}
