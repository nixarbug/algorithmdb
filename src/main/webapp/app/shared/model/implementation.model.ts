import { Moment } from 'moment';
import { ILanguage } from 'app/shared/model/language.model';
import { IAlgorithm } from 'app/shared/model/algorithm.model';

export interface IImplementation {
  id?: number;
  name?: string;
  code?: any;
  codeMarkdown?: any;
  note?: any;
  noteMarkdown?: any;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  language?: ILanguage;
  algorithm?: IAlgorithm;
}

export class Implementation implements IImplementation {
  constructor(
    public id?: number,
    public name?: string,
    public code?: any,
    public codeMarkdown?: any,
    public note?: any,
    public noteMarkdown?: any,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public language?: ILanguage,
    public algorithm?: IAlgorithm
  ) {}
}
