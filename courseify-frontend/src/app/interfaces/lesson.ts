import { Course } from './Course';

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

interface Quiz {
  id: number;
  title: string;
}
