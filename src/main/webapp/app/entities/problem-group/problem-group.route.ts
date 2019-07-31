import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProblemGroup } from 'app/shared/model/problem-group.model';
import { ProblemGroupService } from './problem-group.service';
import { ProblemGroupComponent } from './problem-group.component';
import { ProblemGroupDetailComponent } from './problem-group-detail.component';
import { ProblemGroupUpdateComponent } from './problem-group-update.component';
import { ProblemGroupDeletePopupComponent } from './problem-group-delete-dialog.component';
import { IProblemGroup } from 'app/shared/model/problem-group.model';

@Injectable({ providedIn: 'root' })
export class ProblemGroupResolve implements Resolve<IProblemGroup> {
  constructor(private service: ProblemGroupService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProblemGroup> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProblemGroup>) => response.ok),
        map((problemGroup: HttpResponse<ProblemGroup>) => problemGroup.body)
      );
    }
    return of(new ProblemGroup());
  }
}

export const problemGroupRoute: Routes = [
  {
    path: '',
    component: ProblemGroupComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ProblemGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProblemGroupDetailComponent,
    resolve: {
      problemGroup: ProblemGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ProblemGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProblemGroupUpdateComponent,
    resolve: {
      problemGroup: ProblemGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ProblemGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProblemGroupUpdateComponent,
    resolve: {
      problemGroup: ProblemGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ProblemGroups'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const problemGroupPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProblemGroupDeletePopupComponent,
    resolve: {
      problemGroup: ProblemGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ProblemGroups'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
