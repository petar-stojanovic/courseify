import { Component, OnInit } from '@angular/core';
import { Category } from '../../interfaces/Category';
import { CourseService } from '../../services/course.service';
import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css'],
})
export class CategoriesComponent implements OnInit {
  categories: Category[] = [];

  constructor(private categoryService: CategoryService) {}

  ngOnInit(): void {
    this.categoryService
      .getAllCategories()
      .subscribe((result) => (this.categories = result));
  }
}
