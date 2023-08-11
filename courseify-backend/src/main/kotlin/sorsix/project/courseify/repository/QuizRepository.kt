package sorsix.project.courseify.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import sorsix.project.courseify.domain.Quiz
import sorsix.project.courseify.domain.response.QuizResponse

@Repository
interface QuizRepository : JpaRepository<Quiz, Long> {

    @Query("select q from Quiz q where q.lesson.id = :id")
    fun getQuizByLessonId(id: Long): Quiz?

}
