import { Course } from './Course';
import { Quiz } from './Quiz';

export interface Lesson {
  id: number;
  title: string;
  description: string;
  videoTitle: string;
  videoUrl: string;
  fileTitle: string;
  fileUrl: string;
  course: Course;
  quiz: Quiz;
}
