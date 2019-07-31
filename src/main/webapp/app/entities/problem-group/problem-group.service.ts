import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProblemGroup } from 'app/shared/model/problem-group.model';

type EntityResponseType = HttpResponse<IProblemGroup>;
type EntityArrayResponseType = HttpResponse<IProblemGroup[]>;

@Injectable({ providedIn: 'root' })
export class ProblemGroupService {
  public resourceUrl = SERVER_API_URL + 'api/problem-groups';

  constructor(protected http: HttpClient) {}

  create(problemGroup: IProblemGroup): Observable<EntityResponseType> {
    return this.http.post<IProblemGroup>(this.resourceUrl, problemGroup, { observe: 'response' });
  }

  update(problemGroup: IProblemGroup): Observable<EntityResponseType> {
    return this.http.put<IProblemGroup>(this.resourceUrl, problemGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProblemGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProblemGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
