import { Answer } from './Answer';

export interface Question {
  content: string;
  correctAnswerId: number;
  answers: Answer[];
}
