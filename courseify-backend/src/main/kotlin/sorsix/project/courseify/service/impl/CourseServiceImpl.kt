package sorsix.project.courseify.service.impl

import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import sorsix.project.courseify.api.request.CourseRequest
import sorsix.project.courseify.domain.Category
import sorsix.project.courseify.domain.Course
import sorsix.project.courseify.domain.CourseCategories
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
    val categoryRepository: CategoryRepository,
    val courseCategoriesRepository: CourseCategoriesRepository
) : CourseService {

    private val root: Path = Paths.get("uploads")
    override fun saveCourse(request: CourseRequest): Course {

        val coursePathSlug = request.title.lowercase().replace(" ", "_")

        val pathToUpload = Files.createDirectory(root.resolve(coursePathSlug))

        Files.copy(request.thumbnail.inputStream, pathToUpload.resolve("thumbnail.jpeg"))

        val user = userRepository.findById(request.authorId).get()

        /**
         *   for each category save it if it doesn't exist
         */
        val requestCategories = request.categoryNames
        requestCategories.forEach { category ->
            categoryRepository.findByName(category) ?: categoryRepository.save(Category(0, category))
        }


        val thumbnailPath = pathToUpload.resolve("thumbnail.jpeg").toAbsolutePath().toString()

        val course = Course(
            0, request.title, request.description, thumbnailPath, user
        )

        /**
         *   for each category save it in course_categories
         */

        requestCategories.forEach {
            val category = categoryRepository.findByName(it)
            courseCategoriesRepository.save(CourseCategories(0, course, category!!))
        }

        return courseRepository.save(
            course
        )

    }

    override fun deleteCourse(id: Long) {
        courseRepository.findByIdOrNull(id)?.let {
            val course = this.courseRepository.findById(id).get()
            val coursePath = course.title.lowercase().replace(" ", "_")
            File("uploads/$coursePath").deleteRecursively()
            courseRepository.delete(course)

            val courseCategories = courseCategoriesRepository.findAllByCourse(course)
            courseCategoriesRepository.deleteAll(courseCategories)
        }
    }

    override fun getCourses(search: String?, categoryName: String?): List<Course> {

        return if (search != null && categoryName != null) {
            courseRepository.findAllByCategoryNameAndTitleContainingIgnoreCase(categoryName, search)
        } else if (search != null) {
            courseRepository.findAllByTitleContainingIgnoreCase(search)
        } else if (categoryName != null) {
            courseRepository.findAllByCategoryName(categoryName)
        } else {
            courseRepository.findAll()
        }
    }


    override fun editCourse(id: Long, request: CourseRequest) = courseRepository.findByIdOrNull(id)?.let {
        val oldCourseSlug = it.title.lowercase().replace(" ", "_")
        val oldPath = root.resolve(oldCourseSlug)
        val newCourseSlug = request.title.lowercase().replace(" ", "_")
        val newPath = root.resolve(newCourseSlug)

        if (Files.exists(oldPath) && oldCourseSlug != newCourseSlug) {
            Files.delete(oldPath.resolve("thumbnail.jpeg"))
            Files.move(oldPath, oldPath.resolveSibling(newCourseSlug))
            File(oldPath.toString()).deleteRecursively()
        } else if (Files.exists(oldPath)) {
            Files.delete(oldPath.resolve("thumbnail.jpeg"))
        } else {
            Files.createDirectories(newPath)
        }

        Files.copy(request.thumbnail.inputStream, newPath.resolve("thumbnail.jpeg"))

        val thumbnailPath = newPath.resolve("thumbnail.jpeg").toAbsolutePath().toString()

        val author = userRepository.findById(request.authorId).get()


        /**
         *   for each category save it if it doesn't exist
         */
        val requestCategories = request.categoryNames
        requestCategories.forEach { category ->
            categoryRepository.findByName(category) ?: categoryRepository.save(Category(0, category))
        }


        val course =  Course(
            id = id,
            title = request.title,
            description = request.description,
            thumbnail = thumbnailPath,
            author = author,
        )
        /**
         *   delete the previous categories then for each category save it in course_categories
         */
        courseCategoriesRepository.deleteAll(courseCategoriesRepository.findAllByCourse(course))

        requestCategories.forEach {
            val category = categoryRepository.findByName(it)
            courseCategoriesRepository.save(CourseCategories(0, course, category!!))
        }

        courseRepository.save(
           course
        )
    }

    override fun getThumbnail(courseId: Long): Resource? {
        val coursePath = courseRepository.findById(courseId).get().title.lowercase().replace(" ", "_")

        val thumbnailPath = Paths.get("uploads/$coursePath/thumbnail.jpeg")

        return ByteArrayResource(Files.readAllBytes(thumbnailPath))
    }

}

