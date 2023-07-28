package sorsix.project.courseify.config

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.annotation.PostConstruct
import jakarta.persistence.Column
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import sorsix.project.courseify.api.request.RegisterRequest
import sorsix.project.courseify.domain.*
import sorsix.project.courseify.repository.CategoryRepository
import sorsix.project.courseify.repository.CourseRepository
import sorsix.project.courseify.repository.LessonRepository
import sorsix.project.courseify.repository.UserRepository
import sorsix.project.courseify.service.definitions.AuthService
import sorsix.project.courseify.service.definitions.QuestionService
import sorsix.project.courseify.service.definitions.QuizService

@Component
class DataInitializer() {


    @Autowired
    lateinit var authService: AuthService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var lessonRepository: LessonRepository

    @Autowired
    lateinit var quizService: QuizService

    @Autowired
    lateinit var questionService: QuestionService


    @PostConstruct
    fun init() {
        val user1 = RegisterRequest(
            firstName = "Petar",
            lastName = "Stojanovic",
            email = "pepi@yahoo.com",
            password = "Test123!",
            confirmPassword = "Test123!",
            username = "petar",
            role = Role.USER
        )
        val user2 = RegisterRequest(
            firstName = "Bojan",
            lastName = "Ristevski",
            email = "bojan@gmail.com",
            password = "Bojan123!",
            confirmPassword = "Bojan123!",
            username = "bojan",
            role = Role.USER
        )
        authService.register(user1)
        authService.register(user2)
        val author1 = userRepository.existsByUsername(user1.username)
        val author2 = userRepository.existsByUsername(user2.username)

        val category1 = Category(0, "Java Programming")
        val category2 = Category(0, "Python Programming")
        categoryRepository.save(category1)
        categoryRepository.save(category2)

        val course1 = Course(
            0,
            "Java Programming Course",
            "Java Programming Course Description",
            "Thumbnail Java",
            author1!!,
            category1,
        )


        val course2 = Course(
            0,
            "Python Programming Course For Beginners",
            "Python Programming Course For Beginners Description",
            "Thumbnail Python",
            author2!!,
            category2,
        )

        courseRepository.save(course1)
        courseRepository.save(course2)

        val lesson1 = Lesson(
            id= 0,
            title= "Lesson Title",
            description= "Lesson Description",
            videoTitle= "Video Title",
            videoUrl= "Video Url",
            fileTitle= "File Title",
            fileUrl= "File Url",
            course= course1,
            quiz= null
        )


        val lesson2 = Lesson(
            id= 0,
            title= "Lesson Title 2",
            description= "Lesson Description 2",
            videoTitle= "Video Title 2",
            videoUrl= "Video Url 2",
            fileTitle= "File Title 2",
            fileUrl= "File Url 2",
            course= course1,
            quiz= null
        )

        lessonRepository.save(lesson1)
        lessonRepository.save(lesson2)
    }


}
