package sorsix.project.courseify.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import sorsix.project.courseify.api.request.LessonRequest
import sorsix.project.courseify.domain.Lesson
import sorsix.project.courseify.domain.response.ResponseMessage
import sorsix.project.courseify.repository.LessonRepository
import sorsix.project.courseify.service.LessonService


@RestController
@RequestMapping("/api/lesson")
class LessonController(val lessonRepository: LessonRepository, val lessonService: LessonService) {

    @GetMapping(path = ["", "/"])
    fun getAllLessons(): List<Lesson> = lessonRepository.findAll()

    @GetMapping("/{id}")
    fun getAllCourseLessons(@PathVariable id: Long): List<Lesson> = lessonRepository.findAllByCourseId(id)


    @PostMapping("/save")
    fun saveLesson(@ModelAttribute request: LessonRequest): ResponseEntity<ResponseMessage?> {
        var message = ""
        return try {
            lessonService.save(request)
            message = "Uploaded the video successfully: ${request.videoTitle} and file: ${request.fileTitle}"
            ResponseEntity.status(HttpStatus.OK).body<ResponseMessage?>(ResponseMessage(message))
        } catch (e: Exception) {
            message = "Could not upload the video: ${request.videoTitle}. Error: " + e.message
            ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body<ResponseMessage?>(ResponseMessage(message))
        }
    }

    @DeleteMapping("/{id}")
    fun deleteLesson(@PathVariable id: Long) {
        lessonService.delete(id)
    }

}
