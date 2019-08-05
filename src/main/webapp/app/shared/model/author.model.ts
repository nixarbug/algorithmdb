import { Moment } from 'moment';
import { IAlgorithm } from 'app/shared/model/algorithm.model';

export interface IAuthor {
  id?: number;
  name?: string;
  pictureContentType?: string;
  picture?: any;
  info?: any;
  infoMarkdown?: any;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  algorithms?: IAlgorithm[];
}

export class Author implements IAuthor {
  constructor(
    public id?: number,
    public name?: string,
    public pictureContentType?: string,
    public picture?: any,
    public info?: any,
    public infoMarkdown?: any,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public algorithms?: IAlgorithm[]
  ) {}
}
