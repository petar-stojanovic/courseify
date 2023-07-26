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


    @GetMapping
    fun getAllCourses(@RequestParam search: String?): List<Course> = courseService.getCourses(search)

    @PostMapping("/save")
    fun saveLesson(@ModelAttribute request: CourseRequest): ResponseEntity<*> {
        return courseService.saveCourse(request).let { ResponseEntity.ok(it) }
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
        val courseIds = userTakesCourses.map { it.id }
        return courseRepository.findAllByIdIn(courseIds)
    }

    @PutMapping("/{id}")
    fun editCourse(@PathVariable id: Long, @ModelAttribute request: CourseRequest): ResponseEntity<*>{
        return courseService.editCourse(id, request)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course with id: $id not found")
    }


}
