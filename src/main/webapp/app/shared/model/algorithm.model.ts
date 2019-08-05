import { Moment } from 'moment';
import { IImplementation } from 'app/shared/model/implementation.model';
import { IFunctionClass } from 'app/shared/model/function-class.model';
import { IAuthor } from 'app/shared/model/author.model';
import { ITag } from 'app/shared/model/tag.model';
import { IProblem } from 'app/shared/model/problem.model';

export interface IAlgorithm {
  id?: number;
  name?: string;
  input?: string;
  output?: string;
  idea?: any;
  ideaMarkdown?: any;
  description?: any;
  descriptionMarkdown?: any;
  realLifeUse?: any;
  realLifeUseMarkdown?: any;
  pseudocode?: any;
  pseudocodeMarkdown?: any;
  flowchart?: any;
  flowchartMarkdown?: any;
  flowchartImageContentType?: string;
  flowchartImage?: any;
  complexityAnalysis?: any;
  complexityAnalysisMarkdown?: any;
  correctnessProof?: any;
  correctnessProofMarkdown?: any;
  averageStars?: number;
  totalFavs?: number;
  weightedRating?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  implementations?: IImplementation[];
  worstCaseComplexity?: IFunctionClass;
  averageCaseComplexity?: IFunctionClass;
  bestCaseComplexity?: IFunctionClass;
  authors?: IAuthor[];
  tags?: ITag[];
  problems?: IProblem[];
}

export class Algorithm implements IAlgorithm {
  constructor(
    public id?: number,
    public name?: string,
    public input?: string,
    public output?: string,
    public idea?: any,
    public ideaMarkdown?: any,
    public description?: any,
    public descriptionMarkdown?: any,
    public realLifeUse?: any,
    public realLifeUseMarkdown?: any,
    public pseudocode?: any,
    public pseudocodeMarkdown?: any,
    public flowchart?: any,
    public flowchartMarkdown?: any,
    public flowchartImageContentType?: string,
    public flowchartImage?: any,
    public complexityAnalysis?: any,
    public complexityAnalysisMarkdown?: any,
    public correctnessProof?: any,
    public correctnessProofMarkdown?: any,
    public averageStars?: number,
    public totalFavs?: number,
    public weightedRating?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public implementations?: IImplementation[],
    public worstCaseComplexity?: IFunctionClass,
    public averageCaseComplexity?: IFunctionClass,
    public bestCaseComplexity?: IFunctionClass,
    public authors?: IAuthor[],
    public tags?: ITag[],
    public problems?: IProblem[]
  ) {}
}
