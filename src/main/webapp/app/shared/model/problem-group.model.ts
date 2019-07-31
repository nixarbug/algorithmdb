import { IProblem } from 'app/shared/model/problem.model';

export interface IProblemGroup {
  id?: number;
  name?: string;
  problems?: IProblem[];
}

export class ProblemGroup implements IProblemGroup {
  constructor(public id?: number, public name?: string, public problems?: IProblem[]) {}
}
