package sorsix.project.courseify.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sorsix.project.courseify.domain.UserCreatedCourse
import sorsix.project.courseify.domain.UserTakesCourse
import sorsix.project.courseify.repository.UserCreatedRepository
import sorsix.project.courseify.repository.UserTakesCourseRepository

@RestController
@RequestMapping("/api/relation")
class UserCourseRelationController(val userCreatedRepository: UserCreatedRepository,
    val userTakesCourseRepository: UserTakesCourseRepository) {

    @GetMapping("/created")
    fun getAllCreated(): List<UserCreatedCourse> = userCreatedRepository.findAll()

    @GetMapping("/takes")
    fun getAllTakes(): List<UserTakesCourse> = userTakesCourseRepository.findAll()

}
