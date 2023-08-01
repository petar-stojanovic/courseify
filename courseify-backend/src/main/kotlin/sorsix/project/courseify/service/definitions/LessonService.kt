package sorsix.project.courseify.service.definitions

import org.springframework.core.io.Resource
import sorsix.project.courseify.api.request.LessonRequest
import sorsix.project.courseify.domain.Lesson

interface LessonService {
    fun save(request: LessonRequest): Lesson

    fun getVideo(lessonId: Long): Resource?

    fun delete(id: Long)

    fun editLesson(id: Long, request: LessonRequest): Lesson?

    fun getFile(lessonId: Long): Resource?
}
