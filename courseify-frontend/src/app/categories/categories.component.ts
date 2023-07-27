import { Component, OnInit } from '@angular/core';
import { Category } from '../interfaces/category';
import { CourseService } from '../services/course.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {
  categories: Category[] = []

  constructor(private courseService: CourseService){}

  ngOnInit(): void {
    this.courseService.getCategories().subscribe(result => this.categories = result)
  }
}
