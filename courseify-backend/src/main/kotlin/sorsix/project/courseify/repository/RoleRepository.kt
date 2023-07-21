package sorsix.project.courseify.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sorsix.project.courseify.domain.Role

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
}
