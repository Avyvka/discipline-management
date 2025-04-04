import { IdentifiableEntity } from './identifiable-entity';

export interface Person extends IdentifiableEntity {
    firstName: string;
    lastName: string;
    middleName?: string;
    age: number;
}