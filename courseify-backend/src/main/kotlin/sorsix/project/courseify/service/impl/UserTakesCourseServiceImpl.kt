package sorsix.project.courseify.service.impl

import org.springframework.stereotype.Service
import sorsix.project.courseify.api.request.UserTakesCourseRequest
import sorsix.project.courseify.domain.UserTakesCourse
import sorsix.project.courseify.repository.CourseRepository
import sorsix.project.courseify.repository.UserRepository
import sorsix.project.courseify.repository.UserTakesCourseRepository
import sorsix.project.courseify.service.definitions.UserTakesCourseService
import java.time.LocalDate

@Service
class UserTakesCourseServiceImpl(val userTakesCourseRepository: UserTakesCourseRepository,
    val courseRepository: CourseRepository,
    val userRepository: UserRepository): UserTakesCourseService {
    override fun save(request: UserTakesCourseRequest): UserTakesCourse {
        val course = courseRepository.findById(request.courseId).get()
        val user = userRepository.findById(request.userId).get()
        return userTakesCourseRepository.save(UserTakesCourse(0, user, course, LocalDate.now(), null))
    }

    override fun checkEnrolledUser(request: UserTakesCourseRequest): Boolean {
        return userTakesCourseRepository.existsByCourseIdAndUserId(request.courseId, request.userId)
    }
}
