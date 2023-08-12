package sorsix.project.courseify.api.request

data class UserCompletedQuizRequest(
    val userId: Long,
    val quizId: Long
) {
}
