package sorsix.project.courseify.service.definitions

import sorsix.project.courseify.api.request.UserTakesCourseRequest
import sorsix.project.courseify.domain.UserTakesCourse
import sorsix.project.courseify.domain.response.ProgressAndUncompletedLessonsResponse

interface UserTakesCourseService {
    fun save(request: UserTakesCourseRequest) : UserTakesCourse?
    fun checkEnrolledUser(request: UserTakesCourseRequest): Boolean
    fun getProgressAndUncompletedLessons(courseId: Long, userId: Long): ProgressAndUncompletedLessonsResponse
}
