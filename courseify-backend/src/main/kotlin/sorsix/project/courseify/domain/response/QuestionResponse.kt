package sorsix.project.courseify.domain.response

import sorsix.project.courseify.domain.Answer

data class QuestionResponse(
    val id: Long,
    val content: String,
    val correctAnswerId: Long,
    val answers: List<Answer>
) {
}
