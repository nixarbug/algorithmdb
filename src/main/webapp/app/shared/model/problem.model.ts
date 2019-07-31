import { Moment } from 'moment';
import { IProblemGroup } from 'app/shared/model/problem-group.model';
import { IAlgorithm } from 'app/shared/model/algorithm.model';

export interface IProblem {
  id?: number;
  name?: string;
  description?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  problemGroups?: IProblemGroup[];
  algorithms?: IAlgorithm[];
}

export class Problem implements IProblem {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public problemGroups?: IProblemGroup[],
    public algorithms?: IAlgorithm[]
  ) {}
}
