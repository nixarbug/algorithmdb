import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISystemSetting } from 'app/shared/model/system-setting.model';

type EntityResponseType = HttpResponse<ISystemSetting>;
type EntityArrayResponseType = HttpResponse<ISystemSetting[]>;

@Injectable({ providedIn: 'root' })
export class SystemSettingService {
  public resourceUrl = SERVER_API_URL + 'api/system-settings';

  constructor(protected http: HttpClient) {}

  create(systemSetting: ISystemSetting): Observable<EntityResponseType> {
    return this.http.post<ISystemSetting>(this.resourceUrl, systemSetting, { observe: 'response' });
  }

  update(systemSetting: ISystemSetting): Observable<EntityResponseType> {
    return this.http.put<ISystemSetting>(this.resourceUrl, systemSetting, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISystemSetting>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISystemSetting[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
