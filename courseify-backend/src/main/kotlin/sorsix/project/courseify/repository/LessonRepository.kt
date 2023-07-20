package sorsix.project.courseify.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import sorsix.project.courseify.domain.Lesson

@Repository
interface LessonRepository : JpaRepository<Lesson, Long> {

    fun findAllByCourseId(id: Long): List<Lesson>
}
