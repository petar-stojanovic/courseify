package sorsix.project.courseify.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sorsix.project.courseify.domain.Question
import sorsix.project.courseify.repository.QuestionRepository

@RestController
@RequestMapping("/api/question")
class QuestionController(val questionRepository: QuestionRepository) {

    @GetMapping("/{id}")
    fun getAllQuestionsForQuiz(@PathVariable id: Long): List<Question> = questionRepository.findAllByQuizId(id)

}
