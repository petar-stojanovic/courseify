package sorsix.project.courseify.domain.response

data class ProgressAndUncompletedLessonsResponse(val progress: Double, val uncompletedLessonIds: List<Long>)
