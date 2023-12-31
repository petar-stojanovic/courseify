package sorsix.project.courseify.service.definitions

import org.springframework.core.io.Resource
import sorsix.project.courseify.api.request.CourseRequest
import sorsix.project.courseify.domain.Course
import sorsix.project.courseify.domain.User
import java.io.ByteArrayOutputStream

interface CourseService {

    fun saveCourse(request: CourseRequest, user: User): Course
    fun deleteCourse(id: Long)

    fun getCourses(search: String?, categoryName: String?): List<Course>
    fun editCourse(id: Long, request: CourseRequest, user: User): Course?
    fun getThumbnail(courseId: Long): Resource?

    fun publishCourse(courseId: Long): Course

    fun generatePdf(courseId: Long, userId: Long): ByteArrayOutputStream
}
