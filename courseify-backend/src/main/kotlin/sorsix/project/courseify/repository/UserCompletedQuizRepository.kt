package sorsix.project.courseify.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sorsix.project.courseify.domain.UserCompletedQuiz

@Repository
interface UserCompletedQuizRepository: JpaRepository<UserCompletedQuiz, Long> {
    fun countByCourseIdAndUserId(courseId: Long, userId: Long): Int

    fun findByCourseIdAndUserId(courseId: Long, userId: Long): List<UserCompletedQuiz>

}
