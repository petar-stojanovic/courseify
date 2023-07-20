package sorsix.project.courseify.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sorsix.project.courseify.domain.Quiz
import sorsix.project.courseify.repository.QuizRepository

@RestController
@RequestMapping("/api/quiz")
class QuizController(val quizRepository: QuizRepository) {

    @GetMapping()
    fun getAll(): List<Quiz> = quizRepository.findAll()

    @GetMapping("/{id}")
    fun getQuizWithLessonId(@PathVariable id: Long) = quizRepository.getQuizByLessonId(id)

}
