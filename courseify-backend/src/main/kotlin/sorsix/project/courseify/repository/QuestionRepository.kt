package sorsix.project.courseify.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sorsix.project.courseify.domain.Question

@Repository
interface QuestionRepository : JpaRepository<Question, Long> {

    fun findAllByQuizId(id: Long): List<Question>
}
