package sorsix.project.courseify.service.definitions

import sorsix.project.courseify.api.request.QuizRequest
import sorsix.project.courseify.domain.Quiz
import sorsix.project.courseify.domain.response.QuizResponse

interface QuizService {
    fun save(request: QuizRequest): Quiz
    fun getQuiz(id: Long): QuizResponse?
}
