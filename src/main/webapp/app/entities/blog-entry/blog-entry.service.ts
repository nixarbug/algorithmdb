import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBlogEntry } from 'app/shared/model/blog-entry.model';

type EntityResponseType = HttpResponse<IBlogEntry>;
type EntityArrayResponseType = HttpResponse<IBlogEntry[]>;

@Injectable({ providedIn: 'root' })
export class BlogEntryService {
  public resourceUrl = SERVER_API_URL + 'api/blog-entries';

  constructor(protected http: HttpClient) {}

  create(blogEntry: IBlogEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(blogEntry);
    return this.http
      .post<IBlogEntry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(blogEntry: IBlogEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(blogEntry);
    return this.http
      .put<IBlogEntry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBlogEntry>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBlogEntry[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(blogEntry: IBlogEntry): IBlogEntry {
    const copy: IBlogEntry = Object.assign({}, blogEntry, {
      dateCreated: blogEntry.dateCreated != null && blogEntry.dateCreated.isValid() ? blogEntry.dateCreated.toJSON() : null,
      dateUpdated: blogEntry.dateUpdated != null && blogEntry.dateUpdated.isValid() ? blogEntry.dateUpdated.toJSON() : null
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
      res.body.forEach((blogEntry: IBlogEntry) => {
        blogEntry.dateCreated = blogEntry.dateCreated != null ? moment(blogEntry.dateCreated) : null;
        blogEntry.dateUpdated = blogEntry.dateUpdated != null ? moment(blogEntry.dateUpdated) : null;
      });
    }
    return res;
  }
}
