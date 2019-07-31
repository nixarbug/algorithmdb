import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IImplementation } from 'app/shared/model/implementation.model';

type EntityResponseType = HttpResponse<IImplementation>;
type EntityArrayResponseType = HttpResponse<IImplementation[]>;

@Injectable({ providedIn: 'root' })
export class ImplementationService {
  public resourceUrl = SERVER_API_URL + 'api/implementations';

  constructor(protected http: HttpClient) {}

  create(implementation: IImplementation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(implementation);
    return this.http
      .post<IImplementation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(implementation: IImplementation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(implementation);
    return this.http
      .put<IImplementation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IImplementation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IImplementation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(implementation: IImplementation): IImplementation {
    const copy: IImplementation = Object.assign({}, implementation, {
      dateCreated: implementation.dateCreated != null && implementation.dateCreated.isValid() ? implementation.dateCreated.toJSON() : null,
      dateUpdated: implementation.dateUpdated != null && implementation.dateUpdated.isValid() ? implementation.dateUpdated.toJSON() : null
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
      res.body.forEach((implementation: IImplementation) => {
        implementation.dateCreated = implementation.dateCreated != null ? moment(implementation.dateCreated) : null;
        implementation.dateUpdated = implementation.dateUpdated != null ? moment(implementation.dateUpdated) : null;
      });
    }
    return res;
  }
}
