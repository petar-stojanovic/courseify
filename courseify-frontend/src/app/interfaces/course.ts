import { Category } from "./Category";


export interface Course {
  id: number;
  title: string;
  description: string;
  author: Author;
  category: Category;
  thumbnail: string;
}

interface Author {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  username: string;
  role: Role;
}

interface Role {
  id: number;
  roleName: string;
}
