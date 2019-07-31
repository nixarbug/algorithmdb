import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Implementation } from 'app/shared/model/implementation.model';
import { ImplementationService } from './implementation.service';
import { ImplementationComponent } from './implementation.component';
import { ImplementationDetailComponent } from './implementation-detail.component';
import { ImplementationUpdateComponent } from './implementation-update.component';
import { ImplementationDeletePopupComponent } from './implementation-delete-dialog.component';
import { IImplementation } from 'app/shared/model/implementation.model';

@Injectable({ providedIn: 'root' })
export class ImplementationResolve implements Resolve<IImplementation> {
  constructor(private service: ImplementationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IImplementation> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Implementation>) => response.ok),
        map((implementation: HttpResponse<Implementation>) => implementation.body)
      );
    }
    return of(new Implementation());
  }
}

export const implementationRoute: Routes = [
  {
    path: '',
    component: ImplementationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Implementations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ImplementationDetailComponent,
    resolve: {
      implementation: ImplementationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Implementations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ImplementationUpdateComponent,
    resolve: {
      implementation: ImplementationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Implementations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ImplementationUpdateComponent,
    resolve: {
      implementation: ImplementationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Implementations'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const implementationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ImplementationDeletePopupComponent,
    resolve: {
      implementation: ImplementationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Implementations'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
