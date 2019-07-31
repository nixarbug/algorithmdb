import { IAlgorithm } from 'app/shared/model/algorithm.model';
import { IBlogEntry } from 'app/shared/model/blog-entry.model';

export const enum TagType {
  ALGORITHM = 'ALGORITHM',
  BLOG = 'BLOG'
}

export interface ITag {
  id?: number;
  name?: string;
  type?: TagType;
  algorithms?: IAlgorithm[];
  blogEntries?: IBlogEntry[];
}

export class Tag implements ITag {
  constructor(
    public id?: number,
    public name?: string,
    public type?: TagType,
    public algorithms?: IAlgorithm[],
    public blogEntries?: IBlogEntry[]
  ) {}
}
