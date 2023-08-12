package sorsix.project.courseify.service.definitions

interface UserCompletedQuizService {
    fun save(userId: Long, quizId: Long)

}
