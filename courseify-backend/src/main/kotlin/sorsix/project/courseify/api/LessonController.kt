package sorsix.project.courseify.api

import org.springframework.core.io.Resource
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sorsix.project.courseify.api.request.LessonRequest
import sorsix.project.courseify.domain.Lesson
import sorsix.project.courseify.domain.response.ResponseMessage
import sorsix.project.courseify.repository.LessonRepository
import sorsix.project.courseify.service.definitions.LessonService


@RestController
@RequestMapping("/api/lesson")
class LessonController(val lessonRepository: LessonRepository, val lessonService: LessonService) {

    @GetMapping(path = ["", "/"])
    fun getAllLessons(): List<Lesson> = lessonRepository.findAll()

    @GetMapping("/{id}")
    fun getLesson(@PathVariable id: Long): ResponseEntity<*> = lessonRepository.findByIdOrNull(id)
        ?.let { ResponseEntity.ok(it) }?: ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body("The lesson with id: $id could not be found!")


    @PostMapping("/save")
    fun saveLesson(@ModelAttribute request: LessonRequest): ResponseEntity<*> = lessonService.save(request).let { ResponseEntity.ok(it) }

    @DeleteMapping("/{id}")
    fun deleteLesson(@PathVariable id: Long) {
        lessonService.delete(id)
    }

    @PutMapping("/{id}")
    fun editLesson(@PathVariable id: Long, @ModelAttribute request: LessonRequest): ResponseEntity<*>{
        return lessonService.editLesson(id, request)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson with id: $id could not be found")
    }

    @GetMapping("/{id}/file")
    fun getThumbnail(
        @PathVariable id: Long
    ): ResponseEntity<Resource> {

        val thumbnailData = lessonService.getFile(id)

        val headers = HttpHeaders()
        headers.setContentDispositionFormData("inline", "file.pdf")
        headers.contentType = MediaType.parseMediaType("application/pdf")

        return ResponseEntity(thumbnailData, headers, HttpStatus.OK)
    }

}
