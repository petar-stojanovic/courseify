package sorsix.project.courseify.service.definitions

import sorsix.project.courseify.api.request.CourseRequest
import sorsix.project.courseify.domain.Course

interface CourseService {

    fun saveCourse(request: CourseRequest): Course
    fun deleteCourse(id: Long)
    fun getCourses(search: String?): List<Course>
    fun editCourse(id: Long, request: CourseRequest): Course?
}
