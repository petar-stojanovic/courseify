package sorsix.project.courseify.service

import org.springframework.web.multipart.MultipartFile
import sorsix.project.courseify.api.request.LessonRequest
import sorsix.project.courseify.domain.Lesson

interface LessonService {
    fun save(request: LessonRequest)
}
