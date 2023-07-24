package sorsix.project.courseify.service.impl

import org.springframework.stereotype.Service
import sorsix.project.courseify.api.request.CourseRequest
import sorsix.project.courseify.domain.Course
import sorsix.project.courseify.repository.*
import sorsix.project.courseify.service.definitions.CourseService
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
class CourseServiceImpl(
    val courseRepository: CourseRepository,
    val userRepository: UserRepository,
    val categoryRepository: CategoryRepository
) : CourseService {

    private val root: Path = Paths.get("uploads")
    override fun saveCourse(request: CourseRequest) {

        val coursePathSlug = request.title.lowercase().replace(" ", "_")

        Files.createDirectory(root.resolve(coursePathSlug))

        val user = userRepository.findById(request.authorId).get()
        val category = categoryRepository.findById(request.categoryId).get()

        val course = courseRepository.save(
            Course(
                0, request.title, "", "",
                user, category
            )
        )

    }

    override fun deleteCourse(id: Long) {

        val course = this.courseRepository.findById(id).get()
        val coursePath = course.title.lowercase().replace(" ", "_")
        File("uploads/$coursePath").deleteRecursively()

        courseRepository.delete(course)
    }
}
