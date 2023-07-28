package sorsix.project.courseify.config

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import sorsix.project.courseify.api.request.RegisterRequest
import sorsix.project.courseify.domain.Role
import sorsix.project.courseify.service.definitions.AuthService

@Component
class DataInitializer() {


    @Autowired
    lateinit var authService: AuthService

    @PostConstruct
    fun init() {
        val user1 = RegisterRequest(
            firstName = "petar",
            lastName = "stojanovic",
            email = "pepi@yahoo.com",
            password = "Test123!",
            confirmPassword = "Test123!",
            username = "pepipepei",
            role = Role.USER
        )
        val user2 = RegisterRequest(
            firstName = "Bojan",
            lastName = "Ristevski",
            email = "bojan@gmail.com",
            password = "Bojan123!",
            confirmPassword = "Bojan123!",
            username = "bojan",
            role = Role.USER
        )
        authService.register(user1)
        authService.register(user2)
    }


}
