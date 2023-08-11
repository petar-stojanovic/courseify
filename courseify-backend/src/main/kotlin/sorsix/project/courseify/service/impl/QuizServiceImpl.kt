package sorsix.project.courseify.service.impl

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import sorsix.project.courseify.api.request.QuizRequest
import sorsix.project.courseify.domain.Answer
import sorsix.project.courseify.domain.Question
import sorsix.project.courseify.domain.Quiz
import sorsix.project.courseify.domain.response.QuestionResponse
import sorsix.project.courseify.domain.response.QuizResponse
import sorsix.project.courseify.repository.AnswerRepository
import sorsix.project.courseify.repository.LessonRepository
import sorsix.project.courseify.repository.QuestionRepository
import sorsix.project.courseify.repository.QuizRepository
import sorsix.project.courseify.service.definitions.QuizService

@Service
class QuizServiceImpl(
    val lessonRepository: LessonRepository,
    val quizRepository: QuizRepository,
    val answerRepository: AnswerRepository,
    val questionRepository: QuestionRepository

) : QuizService {
    @Transactional
    override fun save(request: QuizRequest): Quiz {
        val lesson = lessonRepository.findById(request.lessonId).get()

        val quiz = quizRepository.save(Quiz(0, request.title, lesson))
        //save answers
        request.questions.forEach {
            val question = questionRepository.save(Question(0, it.content, quiz, null))
            val correctAnswer = it.answers[it.correctAnswerId.toInt()]
            it.answers.forEach {
                val answer = Answer(0, it, question)
                answerRepository.save(answer)
                if (it == correctAnswer) {
                    questionRepository.save(question.copy(correctAnswer = answer))
                }
            }
        }
        val copiedLesson = lesson.copy(quiz = quiz)
        lessonRepository.save(copiedLesson)
        return quiz
    }

    override fun getQuiz(id: Long): QuizResponse? {
        val quiz = quizRepository.getQuizByLessonId(id)
        val questions = ArrayList<QuestionResponse>()
        questionRepository.findAllByQuizId(quiz!!.id)
            .map { QuestionResponse(it.id, it.content, it.correctAnswer!!.id, mutableListOf()) }
            .forEach {
                val answers = answerRepository.getAllByQuestionId(it.id)
                questions.add(it.copy(answers = answers))
            }
        return QuizResponse(quiz.id, quiz.title, questions)
    }
}
