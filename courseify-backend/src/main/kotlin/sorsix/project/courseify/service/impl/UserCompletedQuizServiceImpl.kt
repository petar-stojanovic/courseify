package sorsix.project.courseify.service.impl

import org.springframework.stereotype.Service
import sorsix.project.courseify.domain.UserCompletedQuiz
import sorsix.project.courseify.repository.*
import sorsix.project.courseify.service.definitions.UserCompletedQuizService

@Service
class UserCompletedQuizServiceImpl
    (
    val userRepository: UserRepository,
    val quizRepository: QuizRepository,
    val userCompletedQuizRepository: UserCompletedQuizRepository,
    val lessonRepository: LessonRepository,
    val userTakesCourseRepository: UserTakesCourseRepository
) : UserCompletedQuizService {
    override fun save(userId: Long, quizId: Long) {

        val user = userRepository.findById(userId).get()
        val quiz = quizRepository.findById(quizId).get()
        val course = quiz.lesson.course;
        userCompletedQuizRepository.save(UserCompletedQuiz(0, user, quiz, course))

    }


}
