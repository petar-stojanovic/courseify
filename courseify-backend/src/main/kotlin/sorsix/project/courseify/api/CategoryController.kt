package sorsix.project.courseify.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sorsix.project.courseify.domain.Category
import sorsix.project.courseify.repository.CategoryRepository

@RestController
@RequestMapping("/api/category")
class CategoryController(val categoryRepository: CategoryRepository) {

    @GetMapping
    fun getAllCategories(): List<Category> {
        println("bla bla")
        return categoryRepository.findAll()
    }
}
