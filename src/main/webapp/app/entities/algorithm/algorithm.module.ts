import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlgorithmdbSharedModule } from 'app/shared';
import {
  AlgorithmComponent,
  AlgorithmDetailComponent,
  AlgorithmInfoComponent,
  AlgorithmUpdateComponent,
  AlgorithmDeletePopupComponent,
  AlgorithmDeleteDialogComponent,
  algorithmRoute,
  algorithmPopupRoute
} from './';

const ENTITY_STATES = [...algorithmRoute, ...algorithmPopupRoute];

@NgModule({
  imports: [AlgorithmdbSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AlgorithmComponent,
    AlgorithmDetailComponent,
    AlgorithmInfoComponent,
    AlgorithmUpdateComponent,
    AlgorithmDeleteDialogComponent,
    AlgorithmDeletePopupComponent
  ],
  entryComponents: [AlgorithmComponent, AlgorithmUpdateComponent, AlgorithmDeleteDialogComponent, AlgorithmDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlgorithmdbAlgorithmModule {}
