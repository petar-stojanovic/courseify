package sorsix.project.courseify.api.request

data class QuestionRequest(
    val content: String,
    val correctAnswerId: Long,
    val answers: List<String>
)
