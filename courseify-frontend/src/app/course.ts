export interface Course {
  id: number;
  title: string;
  description: string;
  author: Author;
  category: Category;
}

interface Category {
  id: number;
  name: string;
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