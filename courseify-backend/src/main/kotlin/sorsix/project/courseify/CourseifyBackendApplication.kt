package sorsix.project.courseify

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CourseifyBackendApplication

fun main(args: Array<String>) {
    runApplication<CourseifyBackendApplication>(*args)
}
