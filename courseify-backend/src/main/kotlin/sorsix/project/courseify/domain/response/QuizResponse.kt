package sorsix.project.courseify.domain.response

import sorsix.project.courseify.domain.Question

data class QuizResponse(
    val id: Long,
    val title: String,
    val questions: List<QuestionResponse>
) {
}
