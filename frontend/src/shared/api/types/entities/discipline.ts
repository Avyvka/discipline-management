import { Course } from './course';
import { IdentifiableEntity } from './identifiable-entity';
import { Lecturer } from './lecturer';

export interface Discipline extends IdentifiableEntity {
    name: string;
    description: string;
    lecturer: Lecturer;
    course: Course;
}