import { Person } from './person';
import { Discipline } from './discipline';

export interface Lecturer extends Person {
    academicTitle: string;
    disciplines?: Discipline[];
}