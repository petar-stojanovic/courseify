package sorsix.project.courseify.service.impl

import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import sorsix.project.courseify.api.request.CourseRequest
import sorsix.project.courseify.domain.Course
import sorsix.project.courseify.domain.CourseCategories
import sorsix.project.courseify.domain.User
import sorsix.project.courseify.repository.*
import sorsix.project.courseify.service.definitions.CourseService
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
class CourseServiceImpl(
    val courseRepository: CourseRepository,
    val categoryRepository: CategoryRepository,
    val courseCategoriesRepository: CourseCategoriesRepository,
    val lessonRepository: LessonRepository,
    val userTakesCourseRepository: UserTakesCourseRepository
) : CourseService {


    private val root: Path = Paths.get("uploads")
    override fun saveCourse(request: CourseRequest, user: User): Course {

        val coursePathSlug = request.title.toSlug()

        val pathToUpload = Files.createDirectory(root.resolve(coursePathSlug))

        Files.copy(request.thumbnail.inputStream, pathToUpload.resolve("thumbnail.jpeg"))


        val thumbnailPath = pathToUpload.resolve("thumbnail.jpeg").toAbsolutePath().toString()

        val course = Course(
            0, request.title, request.description, thumbnailPath, user
        )

        /**
         *   for each category save it in course_categories
         */
        courseRepository.save(course)
        val requestCategories = request.categoryIds.split(", ").toSet()

        requestCategories.forEach {
            val cat = categoryRepository.findByIdOrNull(it.toLong())
            if (cat != null) {
                courseCategoriesRepository.save(CourseCategories(0, course, cat))
            }
        }
        return course
    }

    override fun deleteCourse(id: Long) {
        courseRepository.findByIdOrNull(id)?.let {
            val course = this.courseRepository.findById(id).get()
            val coursePath = course.title.toSlug()
            File("$root/$coursePath").deleteRecursively()
            val courseCategories = courseCategoriesRepository.findAllByCourse(course)
            courseCategoriesRepository.deleteAll(courseCategories)
            val lessons = lessonRepository.findAllByCourseId(course.id)
            lessonRepository.deleteAll(lessons)
            val userCourses = userTakesCourseRepository.findAllByCourse(course)
            userTakesCourseRepository.deleteAll(userCourses)
            courseRepository.delete(course)
        }
    }

    override fun getCourses(search: String?, categoryName: String?): List<Course> {

        return if (search != null && categoryName != null) {
            courseRepository.findAllByCategoryNameAndTitleContainingIgnoreCaseAndActive(categoryName, search)
        } else if (search != null) {
            courseRepository.findAllByTitleContainingIgnoreCaseAndActiveTrue(search)
        } else if (categoryName != null) {
            courseRepository.findAllByCategoryNameAndActiveTrue(categoryName)
        } else {
            courseRepository.findAllByActiveTrue()
        }
    }


    override fun editCourse(id: Long, request: CourseRequest, user: User) = courseRepository.findByIdOrNull(id)?.let {
        val oldCourseSlug = it.title.toSlug()
        val oldPath = root.resolve(oldCourseSlug)
        val newCourseSlug = request.title.toSlug()
        val newPath = root.resolve(newCourseSlug)

        if (Files.exists(oldPath) && oldCourseSlug != newCourseSlug) {
            Files.delete(oldPath.resolve("thumbnail.jpeg"))
            Files.move(oldPath, oldPath.resolveSibling(newCourseSlug))
            File(oldPath.toString()).deleteRecursively()
        } else if (Files.exists(oldPath)) {
            if (Files.exists(oldPath.resolve("thumbnail.jpeg"))) {
                Files.delete(oldPath.resolve("thumbnail.jpeg"))
            }
        } else {
            Files.createDirectories(newPath)
        }

        Files.copy(request.thumbnail.inputStream, newPath.resolve("thumbnail.jpeg"))

        val thumbnailPath = newPath.resolve("thumbnail.jpeg").toAbsolutePath().toString()

        val course = Course(
            id = id,
            title = request.title,
            description = request.description,
            thumbnail = thumbnailPath,
            author = user,
        )
        /**
         *   delete the previous categories then for each category save it in course_categories
         */
        courseCategoriesRepository.deleteAll(courseCategoriesRepository.findAllByCourse(course))

        val requestCategories = request.categoryIds.split(", ").toSet()

        requestCategories.forEach {
            val category = categoryRepository.findByIdOrNull(it.toLong())
            if (category != null) {
                courseCategoriesRepository.save(CourseCategories(0, course, category))
            }
        }

        courseRepository.save(
            course
        )
    }

    override fun getThumbnail(courseId: Long): Resource? {
        val coursePath = courseRepository.findByIdOrNull(courseId)?.title?.toSlug()

        val thumbnailPath = Paths.get("uploads/$coursePath/thumbnail.jpeg")

        try {
            Files.readAllBytes(thumbnailPath)
        } catch (_: Exception) {
            val defaultImagePath = ClassPathResource("/img/default.png")
            return ByteArrayResource(defaultImagePath.inputStream.readAllBytes())
        }
        return ByteArrayResource(Files.readAllBytes(thumbnailPath))
    }

    override fun publishCourse(courseId: Long): Course {
        val course = courseRepository.findById(courseId).get()
        val updatedCourse = course.copy(isActive = true)
        return courseRepository.save(updatedCourse)
    }

    override fun generatePdf(courseId: Long, userId: Long): ByteArrayOutputStream {
        val userTakesCourse = userTakesCourseRepository.findByCourseIdAndUserId(courseId, userId)

        val pdfData = ByteArrayOutputStream()
        val pdfWriter = PdfWriter(pdfData)
        val pdfDocument = PdfDocument(pdfWriter)
        val document = Document(pdfDocument, PageSize.A4)

        val thumbnailResource = getThumbnail(courseId)
        val thumbnailData = thumbnailResource?.inputStream?.readAllBytes()

        if (thumbnailData != null) {
            val thumbnailImage = Image(ImageDataFactory.create(thumbnailData))
            document.add(thumbnailImage)
        }

        document.add(Paragraph("Course Name: ${userTakesCourse?.course?.title}"))
        document.add(Paragraph("Start Date: ${userTakesCourse?.startDate}"))
        document.add(Paragraph("Completion Date: ${userTakesCourse?.endDate}"))
        document.add(Paragraph("User: ${userTakesCourse?.user?.firstName} ${userTakesCourse?.user?.lastName}"))


        document.close()
        pdfDocument.close()

        return pdfData
    }
}


fun String.toSlug(): String = this.lowercase().replace(" ", "_")
