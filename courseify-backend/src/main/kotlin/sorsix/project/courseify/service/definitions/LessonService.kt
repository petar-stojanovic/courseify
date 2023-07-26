package sorsix.project.courseify.service.definitions

import org.springframework.core.io.Resource
import sorsix.project.courseify.api.request.LessonRequest

interface LessonService {
    fun save(request: LessonRequest)

    fun getLessonVideoData(
        videoTitle: String,
        courseId: Long,
        lessonId: Long
    ): Resource?

    fun delete(id: Long)

    fun editLesson(id: Long, request: LessonRequest)
}
