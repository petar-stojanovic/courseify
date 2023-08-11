package sorsix.project.courseify.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import sorsix.project.courseify.api.request.QuizRequest
import sorsix.project.courseify.domain.Quiz
import sorsix.project.courseify.repository.QuizRepository
import sorsix.project.courseify.service.definitions.QuizService

@RestController
@RequestMapping("/api/quiz")
class QuizController(val quizRepository: QuizRepository, val quizService: QuizService) {

//    @GetMapping()
//    fun getAll(): List<Quiz> = quizRepository.findAll()

    @GetMapping()
    fun getQuizWithLessonId(@RequestParam id: Long): ResponseEntity<*> =
        quizService.getQuiz(id)
        ?.let { ResponseEntity.ok(it) }
        ?: ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("The quiz with lesson id $id was not found!")

    @PostMapping()
    fun saveQuiz(@RequestBody request: QuizRequest): ResponseEntity<*> =
        quizService.save(request).let { ResponseEntity.ok(it) }
}
