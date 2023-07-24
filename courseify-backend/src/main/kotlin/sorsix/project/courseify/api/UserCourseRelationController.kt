package sorsix.project.courseify.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sorsix.project.courseify.domain.UserTakesCourse
import sorsix.project.courseify.repository.UserTakesCourseRepository

@RestController
@RequestMapping("/api/relation")
class UserCourseRelationController(val userTakesCourseRepository: UserTakesCourseRepository) {
    
    @GetMapping("/takes")
    fun getAllTakes(): List<UserTakesCourse> = userTakesCourseRepository.findAll()

}
