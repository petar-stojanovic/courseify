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
  cachedCategories: Category[] | null= [];

  constructor(private categoryService: CategoryService) {}

  ngOnInit(): void {
    this.loadCachedCategories()
  }

  loadCachedCategories(): void {
    const cachedCategories = localStorage.getItem('categories');
    if (cachedCategories) {
      this.cachedCategories = JSON.parse(cachedCategories);
    }else{
      this.categoryService.getAllCategories().subscribe((result) => {
        this.cachedCategories = result;
        localStorage.setItem('categories', JSON.stringify(result));
  
      });
    }
  }

  clearCachedCategories(): void {
    this.cachedCategories = null;
    localStorage.removeItem('user_data');
  }

  getCategories(): Category[] | null {
    return this.cachedCategories;
  }
}
