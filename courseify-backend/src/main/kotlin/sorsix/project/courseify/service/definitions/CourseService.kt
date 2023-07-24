package sorsix.project.courseify.service.definitions

import sorsix.project.courseify.api.request.CourseRequest

interface CourseService {

    fun saveCourse(request: CourseRequest)
    fun deleteCourse(id: Long)
}
