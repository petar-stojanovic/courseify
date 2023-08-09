package sorsix.project.courseify.service.definitions

import sorsix.project.courseify.api.request.QuizRequest
import sorsix.project.courseify.domain.Quiz

interface QuizService {
    fun save(request: QuizRequest): Quiz
}
