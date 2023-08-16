package sorsix.project.courseify.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sorsix.project.courseify.domain.Answer
import sorsix.project.courseify.domain.Question

@Repository
interface AnswerRepository : JpaRepository<Answer, Long> {

    fun getAllByQuestionId(id: Long): List<Answer>

    fun deleteAllByQuestion(question: Question)
}
