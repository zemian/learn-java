import { IAuthor } from 'app/shared/model/author.model';

export interface IBook {
    id?: number;
    title?: string;
    author?: IAuthor;
}

export class Book implements IBook {
    constructor(public id?: number, public title?: string, public author?: IAuthor) {}
}
