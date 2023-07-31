package sorsix.project.courseify.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import sorsix.project.courseify.domain.User

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?

    fun findByUsername(username: String): User?

    @Query("select u from User u where u.username = :username")
    fun existsByUsername(username: String): User?

    @Query("select t.user from Token t where t.token = :token")
    fun findUserByToken(token: String): User
}
