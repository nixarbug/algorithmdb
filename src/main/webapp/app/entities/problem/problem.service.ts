import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProblem } from 'app/shared/model/problem.model';

type EntityResponseType = HttpResponse<IProblem>;
type EntityArrayResponseType = HttpResponse<IProblem[]>;

@Injectable({ providedIn: 'root' })
export class ProblemService {
  public resourceUrl = SERVER_API_URL + 'api/problems';

  constructor(protected http: HttpClient) {}

  create(problem: IProblem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(problem);
    return this.http
      .post<IProblem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(problem: IProblem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(problem);
    return this.http
      .put<IProblem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProblem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProblem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(problem: IProblem): IProblem {
    const copy: IProblem = Object.assign({}, problem, {
      dateCreated: problem.dateCreated != null && problem.dateCreated.isValid() ? problem.dateCreated.toJSON() : null,
      dateUpdated: problem.dateUpdated != null && problem.dateUpdated.isValid() ? problem.dateUpdated.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreated = res.body.dateCreated != null ? moment(res.body.dateCreated) : null;
      res.body.dateUpdated = res.body.dateUpdated != null ? moment(res.body.dateUpdated) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((problem: IProblem) => {
        problem.dateCreated = problem.dateCreated != null ? moment(problem.dateCreated) : null;
        problem.dateUpdated = problem.dateUpdated != null ? moment(problem.dateUpdated) : null;
      });
    }
    return res;
  }
}
