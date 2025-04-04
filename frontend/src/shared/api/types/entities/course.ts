import { IdentifiableEntity } from './identifiable-entity';
import { Discipline } from './discipline';

export interface Course extends IdentifiableEntity {
  number: number;
  disciplines?: Discipline[];
}