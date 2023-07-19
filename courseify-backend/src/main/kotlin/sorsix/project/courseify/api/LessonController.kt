package sorsix.project.courseify.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sorsix.project.courseify.domain.Lesson
import sorsix.project.courseify.repository.LessonRepository

@RestController
@RequestMapping("/api/lesson")
class LessonController(val lessonRepository: LessonRepository) {

    @GetMapping(path = ["", "/"])
    fun getAllLessons(): List<Lesson> = lessonRepository.findAll()


}