package sorsix.project.courseify.service.impl

import org.springframework.stereotype.Service
import sorsix.project.courseify.api.request.QuizRequest
import sorsix.project.courseify.domain.Quiz
import sorsix.project.courseify.repository.LessonRepository
import sorsix.project.courseify.repository.QuizRepository
import sorsix.project.courseify.service.definitions.QuizService

@Service
class QuizServiceImpl(val lessonRepository: LessonRepository,
    val quizRepository: QuizRepository) : QuizService {
    override fun save(request: QuizRequest): Quiz {
        val lesson = lessonRepository.findById(request.lessonId).get()
        val quiz = quizRepository.save(Quiz(0, request.title, lesson))
        val copiedLesson = lesson.copy(quiz = quiz)
        lessonRepository.save(copiedLesson)
        return quiz
    }
}
