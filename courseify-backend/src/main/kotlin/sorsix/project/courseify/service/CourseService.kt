package sorsix.project.courseify.service

import sorsix.project.courseify.api.request.CourseRequest

interface CourseService {

    fun saveCourse(request: CourseRequest)
}
