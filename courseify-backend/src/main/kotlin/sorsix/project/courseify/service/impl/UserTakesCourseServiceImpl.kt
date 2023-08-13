package sorsix.project.courseify.service.impl

import org.springframework.stereotype.Service
import sorsix.project.courseify.api.request.UserTakesCourseRequest
import sorsix.project.courseify.domain.UserTakesCourse
import sorsix.project.courseify.domain.response.ProgressAndUncompletedLessonsResponse
import sorsix.project.courseify.repository.*
import sorsix.project.courseify.service.definitions.UserTakesCourseService
import java.time.LocalDate

@Service
class UserTakesCourseServiceImpl(
    val userTakesCourseRepository: UserTakesCourseRepository,
    val courseRepository: CourseRepository,
    val userRepository: UserRepository,
    private val userCompletedQuizRepository: UserCompletedQuizRepository,
    private val lessonRepository: LessonRepository,
    val quizRepository: QuizRepository
) : UserTakesCourseService {
    override fun save(request: UserTakesCourseRequest): UserTakesCourse {
        val course = courseRepository.findById(request.courseId).get()
        val user = userRepository.findById(request.userId).get()
        return userTakesCourseRepository.save(UserTakesCourse(0, user, course, LocalDate.now(), null, null))
    }

    override fun checkEnrolledUser(request: UserTakesCourseRequest): Boolean {
        return userTakesCourseRepository.existsByCourseIdAndUserId(request.courseId, request.userId)
    }

    override fun getProgressAndUncompletedLessons(courseId: Long, userId: Long): ProgressAndUncompletedLessonsResponse {
        val userTakesCourse = userTakesCourseRepository.findByCourseIdAndUserId(courseId, userId)

        if (userTakesCourse != null) {
            val completedQuizzes = userCompletedQuizRepository.findByCourseIdAndUserId(courseId, userId)
            val completedQuizIds = completedQuizzes.map { it.quiz.id }

            val countCompletedQuiz = completedQuizzes.size
            val countAllQuiz = lessonRepository.findAllByCourseId(courseId).count { it.quiz != null }

            val progress = countCompletedQuiz.toDouble() / countAllQuiz.toDouble()

            val uncompletedLessons = lessonRepository.findAllByCourseId(courseId)
                .filter { it.quiz != null && it.quiz.id !in completedQuizIds }
                .map { it.id }

            userTakesCourseRepository.save(userTakesCourse.copy(progress = progress))

            return ProgressAndUncompletedLessonsResponse(progress, uncompletedLessons)
        }
        return ProgressAndUncompletedLessonsResponse(0.0, listOf())
    }


}
