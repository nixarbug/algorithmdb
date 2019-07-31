import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SystemSetting } from 'app/shared/model/system-setting.model';
import { SystemSettingService } from './system-setting.service';
import { SystemSettingComponent } from './system-setting.component';
import { SystemSettingDetailComponent } from './system-setting-detail.component';
import { SystemSettingUpdateComponent } from './system-setting-update.component';
import { SystemSettingDeletePopupComponent } from './system-setting-delete-dialog.component';
import { ISystemSetting } from 'app/shared/model/system-setting.model';

@Injectable({ providedIn: 'root' })
export class SystemSettingResolve implements Resolve<ISystemSetting> {
  constructor(private service: SystemSettingService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISystemSetting> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SystemSetting>) => response.ok),
        map((systemSetting: HttpResponse<SystemSetting>) => systemSetting.body)
      );
    }
    return of(new SystemSetting());
  }
}

export const systemSettingRoute: Routes = [
  {
    path: '',
    component: SystemSettingComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SystemSettings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SystemSettingDetailComponent,
    resolve: {
      systemSetting: SystemSettingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SystemSettings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SystemSettingUpdateComponent,
    resolve: {
      systemSetting: SystemSettingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SystemSettings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SystemSettingUpdateComponent,
    resolve: {
      systemSetting: SystemSettingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SystemSettings'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const systemSettingPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SystemSettingDeletePopupComponent,
    resolve: {
      systemSetting: SystemSettingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SystemSettings'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
