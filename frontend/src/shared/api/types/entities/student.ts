import { Person } from './person';
import { Course } from './course';

export interface Student extends Person {
    group: string;
    course: Course;
}