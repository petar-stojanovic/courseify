package sorsix.project.courseify.service.definitions

import sorsix.project.courseify.api.request.UserTakesCourseRequest
import sorsix.project.courseify.domain.UserTakesCourse

interface UserTakesCourseService {
    fun save(request: UserTakesCourseRequest) : UserTakesCourse?
    fun checkEnrolledUser(request: UserTakesCourseRequest): Boolean
    fun getProgress(courseId: Long, userId: Long): Double?
}
