package sorsix.project.courseify.config

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import sorsix.project.courseify.api.request.RegisterRequest
import sorsix.project.courseify.domain.*
import sorsix.project.courseify.repository.*
import sorsix.project.courseify.service.definitions.AuthService
import sorsix.project.courseify.service.definitions.QuestionService

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
    lateinit var courseCategoriesRepository: CourseCategoriesRepository

    @Autowired
    lateinit var questionService: QuestionService


//    @PostConstruct
    fun init() {
        val user1 = RegisterRequest(
            firstName = "Petar",
            lastName = "Stojanovic",
            email = "petar@gmail.com",
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
        val category3 = Category(0, "Spring Boot")

        categoryRepository.save(category1)
        categoryRepository.save(category2)
        categoryRepository.save(category3)

        val additionalCategories = listOf(
            "Web Development", "Mobile App Development", "Game Development",
            "Database Management", "DevOps", "Machine Learning", "Artificial Intelligence",
            "Data Science", "Cloud Computing", "Cybersecurity",
            "Frontend Development", "Backend Development", "Full Stack Development",
            "UI/UX Design", "Version Control", "Agile Methodology",
            "Software Testing", "Continuous Integration", "API Development",
            "Microservices", "Blockchain", "IoT Development", "Embedded Systems",
            "Natural Language Processing", "Big Data", "Virtual Reality",
            "Augmented Reality", "Quantum Computing", "Functional Programming",
            "Object-Oriented Programming", "Low-Level Programming", "High-Level Programming",
            "Concurrency", "Parallel Programming", "Responsive Design",
            "RESTful APIs", "GraphQL", "Secure Coding Practices", "Code Optimization",
            "Code Review", "Pair Programming", "Scrum", "Kanban",
            "Software Architecture", "Design Patterns", "Refactoring",
            "Code Documentation", "Legacy Code Maintenance", "Code Quality",
            "Technical Debt", "Software Development Lifecycle", "Project Management",
            "C#", "C++", "Java", "Python", "JavaScript",
            "Ruby", "Swift", "Kotlin", "Go", "Rust",
            "PHP", "Perl", "Scala", "Haskell", "Lua",
            "TypeScript", "Dart", "F#", "Groovy", "Objective-C",
            "R", "Matlab", "Julia", "COBOL", "Fortran",
            "Assembly", "PL/SQL", "T-SQL", "VBScript", "Ada",
            "Lisp", "Prolog", "Scheme", "Smalltalk", "Logo",
            "Clojure", "Elixir", "Erlang", "VHDL", "Verilog",
            "SQL", "HTML", "CSS", "XML", "JSON",
            "Bash", "PowerShell", "Pascal", "Racket", "Ada"
        )

        for (categoryName in additionalCategories) {
            val category = Category(0, categoryName)
            categoryRepository.save(category)
        }
//
//        val course1 = Course(
//            0,
//            "Java Programming Course",
//            "Java Programming Course Description",
//            "Thumbnail Java",
//            author1!!,
//            false
//        )
//
//
//        val course2 = Course(
//            0,
//            "Python Programming Course For Beginners",
//            "Python Programming Course For Beginners Description",
//            "Thumbnail Python",
//            author2!!,
//            true
//        )
//
//        val courseCategories1 = CourseCategories(0, course1, category1)
//        val courseCategories2 = CourseCategories(0, course2, category2)
//        val courseCategories3 = CourseCategories(0, course2, category3)
//
//        courseRepository.save(course1)
//        courseRepository.save(course2)
//
//        courseCategoriesRepository.save(courseCategories1)
//        courseCategoriesRepository.save(courseCategories2)
//        courseCategoriesRepository.save(courseCategories3)
//
//
//        val lesson1 = Lesson(
//            id = 0,
//            title = "Lesson Title",
//            description = "Lesson Description",
//            videoTitle = "Video Title",
//            videoUrl = "Video Url",
//            fileTitle = "File Title",
//            fileUrl = "File Url",
//            course = course1,
//            quiz = null
//        )
//
//
//        val lesson2 = Lesson(
//            id = 0,
//            title = "Lesson Title 2",
//            description = "Lesson Description 2",
//            videoTitle = "Video Title 2",
//            videoUrl = "Video Url 2",
//            fileTitle = "File Title 2",
//            fileUrl = "File Url 2",
//            course = course1,
//            quiz = null
//        )
//
//        val lesson3 = Lesson(
//            id = 0,
//            title = "Lesson Title",
//            description = "Lesson Description",
//            videoTitle = "Video Title",
//            videoUrl = "Video Url",
//            fileTitle = "File Title",
//            fileUrl = "File Url",
//            course = course2,
//            quiz = null
//        )
//
//
//        val lesson4 = Lesson(
//            id = 0,
//            title = "Lesson Title 2",
//            description = "Lesson Description 2",
//            videoTitle = "Video Title 2",
//            videoUrl = "Video Url 2",
//            fileTitle = "File Title 2",
//            fileUrl = "File Url 2",
//            course = course2,
//            quiz = null
//        )
//
//        lessonRepository.save(lesson1)
//        lessonRepository.save(lesson2)
//        lessonRepository.save(lesson3)
//        lessonRepository.save(lesson4)
    }


}
