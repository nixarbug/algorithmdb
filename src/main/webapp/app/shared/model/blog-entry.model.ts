import { Moment } from 'moment';
import { ITag } from 'app/shared/model/tag.model';
import { IBlog } from 'app/shared/model/blog.model';

export interface IBlogEntry {
  id?: number;
  title?: string;
  content?: any;
  contentMarkdown?: any;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  tags?: ITag[];
  blog?: IBlog;
}

export class BlogEntry implements IBlogEntry {
  constructor(
    public id?: number,
    public title?: string,
    public content?: any,
    public contentMarkdown?: any,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public tags?: ITag[],
    public blog?: IBlog
  ) {}
}
