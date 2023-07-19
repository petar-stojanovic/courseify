package sorsix.project.courseify.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sorsix.project.courseify.domain.Course
import sorsix.project.courseify.repository.CourseRepository

@RestController
@RequestMapping("/api/course")
class CourseController(val courseRepository: CourseRepository) {


    @GetMapping(path = ["", "/"])
    fun getAllCourses(): List<Course> = courseRepository.findAll()

}