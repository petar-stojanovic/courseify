import { Category } from "./Category";


export interface Course {
  id: number;
  title: string;
  description: string;
  author: Author;
  category: Category;
  thumbnail: string;
  isActive: boolean;
}

interface Author {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  username: string;
}

