import { Question } from './Question';

export interface Quiz {
  title: string;
  lessonId: number;
  id: number;
  questions: Question[];
}
