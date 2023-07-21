package sorsix.project.courseify.service.impl

import org.springframework.stereotype.Service
import sorsix.project.courseify.api.request.CourseRequest
import sorsix.project.courseify.domain.Course
import sorsix.project.courseify.repository.CategoryRepository
import sorsix.project.courseify.repository.CourseRepository
import sorsix.project.courseify.repository.RoleRepository
import sorsix.project.courseify.repository.UserRepository
import sorsix.project.courseify.service.CourseService
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
        val category = categoryRepository.findById(request.roleId).get()

        courseRepository.save(Course(0, request.title, "", "",
            user, category))

    }
}
