import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlgorithmdbSharedModule } from 'app/shared';
import {
  ProblemGroupComponent,
  ProblemGroupDetailComponent,
  ProblemGroupUpdateComponent,
  ProblemGroupDeletePopupComponent,
  ProblemGroupDeleteDialogComponent,
  problemGroupRoute,
  problemGroupPopupRoute
} from './';

const ENTITY_STATES = [...problemGroupRoute, ...problemGroupPopupRoute];

@NgModule({
  imports: [AlgorithmdbSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProblemGroupComponent,
    ProblemGroupDetailComponent,
    ProblemGroupUpdateComponent,
    ProblemGroupDeleteDialogComponent,
    ProblemGroupDeletePopupComponent
  ],
  entryComponents: [
    ProblemGroupComponent,
    ProblemGroupUpdateComponent,
    ProblemGroupDeleteDialogComponent,
    ProblemGroupDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlgorithmdbProblemGroupModule {}
