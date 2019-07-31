import { IBlogEntry } from 'app/shared/model/blog-entry.model';

export interface IBlog {
  id?: number;
  name?: string;
  handle?: string;
  blogEntries?: IBlogEntry[];
}

export class Blog implements IBlog {
  constructor(public id?: number, public name?: string, public handle?: string, public blogEntries?: IBlogEntry[]) {}
}
