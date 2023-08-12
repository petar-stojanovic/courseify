package sorsix.project.courseify.service.impl

import org.springframework.stereotype.Service
import sorsix.project.courseify.api.request.UserTakesCourseRequest
import sorsix.project.courseify.domain.UserTakesCourse
import sorsix.project.courseify.repository.*
import sorsix.project.courseify.service.definitions.UserTakesCourseService
import java.time.LocalDate

@Service
class UserTakesCourseServiceImpl(
    val userTakesCourseRepository: UserTakesCourseRepository,
    val courseRepository: CourseRepository,
    val userRepository: UserRepository,
    private val userCompletedQuizRepository: UserCompletedQuizRepository,
    private val lessonRepository: LessonRepository
) : UserTakesCourseService {
    override fun save(request: UserTakesCourseRequest): UserTakesCourse {
        val course = courseRepository.findById(request.courseId).get()
        val user = userRepository.findById(request.userId).get()
        return userTakesCourseRepository.save(UserTakesCourse(0, user, course, LocalDate.now(), null, null))
    }

    override fun checkEnrolledUser(request: UserTakesCourseRequest): Boolean {
        return userTakesCourseRepository.existsByCourseIdAndUserId(request.courseId, request.userId)
    }

    override fun getProgress(courseId: Long, userId: Long): Double? {

        val userTakesCourse = userTakesCourseRepository.findByCourseIdAndUserId(courseId, userId)

        if (userTakesCourse != null) {
            val countCompletedQuiz = userCompletedQuizRepository.countByCourseIdAndUserId(courseId, userId)
            val countAllQuiz = lessonRepository.countByCourseId(courseId)

            val progress = countCompletedQuiz.toDouble() / countAllQuiz.toDouble()
            userTakesCourseRepository.save(userTakesCourse.copy(progress = progress))
            return progress
        }
        return null
    }
}
