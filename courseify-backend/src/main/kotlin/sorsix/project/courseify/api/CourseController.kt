package sorsix.project.courseify.api

import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sorsix.project.courseify.api.request.CourseRequest
import sorsix.project.courseify.api.request.UserTakesCourseRequest
import sorsix.project.courseify.domain.Course
import sorsix.project.courseify.domain.Lesson
import sorsix.project.courseify.domain.User
import sorsix.project.courseify.domain.exception.UserNotFoundException
import sorsix.project.courseify.repository.*
import sorsix.project.courseify.security.token.TokenRepository
import sorsix.project.courseify.service.definitions.CourseService
import sorsix.project.courseify.service.definitions.UserTakesCourseService

@RestController
@RequestMapping("/api/course")
class CourseController(
    val courseRepository: CourseRepository,
    val courseService: CourseService,
    val userTakesCourseService: UserTakesCourseService,
    val userTakesCourseRepository: UserTakesCourseRepository,
    val lessonRepository: LessonRepository,
    val tokenRepository: TokenRepository,
    val courseCategoriesRepository: CourseCategoriesRepository,
    val categoryRepository: CategoryRepository
) {

    fun getCurrentUser(request: HttpServletRequest): User {
        val token = tokenRepository.findByToken(request.getHeader("Authorization").substring(7))
            ?: throw UserNotFoundException("User not found")
        return token.user
    }

    @GetMapping
    fun getAllCourses(@RequestParam search: String?, @RequestParam categoryName: String?): List<Course> =
        courseService.getCourses(search, categoryName)

    @GetMapping("/{id}")
    fun getCourse(@PathVariable id: Long): ResponseEntity<*> = courseRepository.findByIdOrNull(id)
        ?.let { ResponseEntity.ok(it) } ?: ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body("The course with id $id does not exist!")

    @GetMapping("/{id}/categories")
    fun getCourseCategories(@PathVariable id: Long): ResponseEntity<*> =
        courseRepository.findByIdOrNull(id)?.let {
            val courseCategories = courseCategoriesRepository.findAllByCourse(it)
            val categoryIds = courseCategories.map { it.category.id }
            ResponseEntity.ok(categoryRepository.findAllByIdIn(categoryIds))
        } ?: ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("There are no categories for course with id:  $id does !")


    @GetMapping("/{id}/lessons")
    fun getCourseLessons(@PathVariable id: Long): List<Lesson> = lessonRepository.findAllByCourseId(id)

    @PostMapping("/save")
    fun saveLesson(@ModelAttribute request: CourseRequest, req: HttpServletRequest): ResponseEntity<*> {
        val user = getCurrentUser(req)
        return courseService.saveCourse(request, user).let { ResponseEntity.ok(it) }
    }

    @DeleteMapping("/{id}")
    fun deleteCourse(@PathVariable id: Long) {
        courseService.deleteCourse(id)
    }

    @PostMapping("/enroll")
    fun enrollCourse(@RequestBody request: UserTakesCourseRequest): ResponseEntity<*> =
        userTakesCourseService.save(request).let { ResponseEntity.ok(it) }

    @GetMapping("/users/{id}")
    fun findAllUserCourses(@PathVariable id: Long): List<Course> {
        val userTakesCourses = userTakesCourseRepository.findAllByUserId(id)
        val courseIds = userTakesCourses.map { it.id }
        return courseRepository.findAllByIdIn(courseIds)
    }

    @PutMapping("/{id}")
    fun editCourse(
        @PathVariable id: Long,
        @ModelAttribute request: CourseRequest,
        req: HttpServletRequest
    ): ResponseEntity<*> {
        val user = getCurrentUser(req)
        return courseService.editCourse(id, request, user)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course with id: $id could not br found")
    }

    @GetMapping("/{id}/edit")
    fun getEditCourse(@PathVariable id: Long): ResponseEntity<*> = courseRepository.findByIdOrNull(id)
        ?.let { ResponseEntity.ok(it) }
        ?: ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The course with id: $id could not be found")


    @GetMapping("/{id}/thumbnail")
    fun getThumbnail(
        @PathVariable id: Long
    ): ResponseEntity<Resource> {

        val thumbnailData = courseService.getThumbnail(id)

        val headers = HttpHeaders()
        headers.setContentDispositionFormData("inline", "thumbnail.jpeg")
        headers.contentType = MediaType.parseMediaType("image/jpeg")

        return ResponseEntity(thumbnailData, headers, HttpStatus.OK)
    }


    @PostMapping("/{id}/publish")
    fun enrollCourse(@PathVariable id: Long): ResponseEntity<*> =
        courseService.publishCourse(id).let { ResponseEntity.ok(it) }

    @PostMapping("/progress")
    fun getProgress(@RequestBody request: UserTakesCourseRequest): ResponseEntity<*> =
        userTakesCourseService.getProgressAndUncompletedLessons(request.courseId, request.userId).let { ResponseEntity.ok(it) }


    @PostMapping("/generate-pdf")
    fun generatePdf(@RequestBody request: UserTakesCourseRequest): ResponseEntity<Resource> {

        val pdfData = courseService.generatePdf(request.courseId,request.userId)

        val headers = HttpHeaders()
        headers.setContentDispositionFormData("inline", "certificate.pdf")
        headers.contentType = MediaType.parseMediaType("application/pdf")

        val pdfResource = ByteArrayResource(pdfData.toByteArray())

        return ResponseEntity(pdfResource, headers, HttpStatus.OK)
    }

}
