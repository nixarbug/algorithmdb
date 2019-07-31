import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlgorithmdbSharedModule } from 'app/shared';
import {
  ImplementationComponent,
  ImplementationDetailComponent,
  ImplementationUpdateComponent,
  ImplementationDeletePopupComponent,
  ImplementationDeleteDialogComponent,
  implementationRoute,
  implementationPopupRoute
} from './';

const ENTITY_STATES = [...implementationRoute, ...implementationPopupRoute];

@NgModule({
  imports: [AlgorithmdbSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ImplementationComponent,
    ImplementationDetailComponent,
    ImplementationUpdateComponent,
    ImplementationDeleteDialogComponent,
    ImplementationDeletePopupComponent
  ],
  entryComponents: [
    ImplementationComponent,
    ImplementationUpdateComponent,
    ImplementationDeleteDialogComponent,
    ImplementationDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlgorithmdbImplementationModule {}
