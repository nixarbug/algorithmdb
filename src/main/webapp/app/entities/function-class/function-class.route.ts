import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FunctionClass } from 'app/shared/model/function-class.model';
import { FunctionClassService } from './function-class.service';
import { FunctionClassComponent } from './function-class.component';
import { FunctionClassDetailComponent } from './function-class-detail.component';
import { FunctionClassUpdateComponent } from './function-class-update.component';
import { FunctionClassDeletePopupComponent } from './function-class-delete-dialog.component';
import { IFunctionClass } from 'app/shared/model/function-class.model';

@Injectable({ providedIn: 'root' })
export class FunctionClassResolve implements Resolve<IFunctionClass> {
  constructor(private service: FunctionClassService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFunctionClass> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FunctionClass>) => response.ok),
        map((functionClass: HttpResponse<FunctionClass>) => functionClass.body)
      );
    }
    return of(new FunctionClass());
  }
}

export const functionClassRoute: Routes = [
  {
    path: '',
    component: FunctionClassComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FunctionClasses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FunctionClassDetailComponent,
    resolve: {
      functionClass: FunctionClassResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FunctionClasses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FunctionClassUpdateComponent,
    resolve: {
      functionClass: FunctionClassResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FunctionClasses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FunctionClassUpdateComponent,
    resolve: {
      functionClass: FunctionClassResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FunctionClasses'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const functionClassPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FunctionClassDeletePopupComponent,
    resolve: {
      functionClass: FunctionClassResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FunctionClasses'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
