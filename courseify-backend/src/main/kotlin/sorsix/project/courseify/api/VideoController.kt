package sorsix.project.courseify.api

import org.springframework.core.io.*
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sorsix.project.courseify.service.definitions.LessonService
import java.nio.file.*


@RestController
@RequestMapping("/api/video")
class VideoController(
    val lessonService: LessonService
) {

    @GetMapping()
    fun serveVideo(
        @RequestParam videoTitle: String,
        @RequestParam courseId: Long,
        @RequestParam lessonId: Long
    ): ResponseEntity<Resource> {

        val videoData = lessonService.getLessonVideoData(videoTitle,courseId,lessonId)

        val headers = HttpHeaders()
        headers.setContentDispositionFormData("inline", "$videoTitle.mp4")
        headers.contentType = MediaType.parseMediaType("video/mp4")

        return ResponseEntity(videoData, headers, HttpStatus.OK)
    }
}
