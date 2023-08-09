package sorsix.project.courseify.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sorsix.project.courseify.api.request.QuizRequest
import sorsix.project.courseify.domain.Quiz
import sorsix.project.courseify.repository.QuizRepository
import sorsix.project.courseify.service.definitions.QuizService

@RestController
@RequestMapping("/api/quiz")
class QuizController(val quizRepository: QuizRepository, val quizService: QuizService) {

    @GetMapping()
    fun getAll(): List<Quiz> = quizRepository.findAll()

    @GetMapping("/{id}")
    fun getQuizWithLessonId(@PathVariable id: Long) = quizRepository.getQuizByLessonId(id)

    @PostMapping()
    fun saveQuiz(@RequestBody request: QuizRequest): ResponseEntity<*> =
        quizService.save(request).let { ResponseEntity.ok(it) }
}
