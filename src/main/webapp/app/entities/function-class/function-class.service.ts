import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFunctionClass } from 'app/shared/model/function-class.model';

type EntityResponseType = HttpResponse<IFunctionClass>;
type EntityArrayResponseType = HttpResponse<IFunctionClass[]>;

@Injectable({ providedIn: 'root' })
export class FunctionClassService {
  public resourceUrl = SERVER_API_URL + 'api/function-classes';

  constructor(protected http: HttpClient) {}

  create(functionClass: IFunctionClass): Observable<EntityResponseType> {
    return this.http.post<IFunctionClass>(this.resourceUrl, functionClass, { observe: 'response' });
  }

  update(functionClass: IFunctionClass): Observable<EntityResponseType> {
    return this.http.put<IFunctionClass>(this.resourceUrl, functionClass, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFunctionClass>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFunctionClass[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
