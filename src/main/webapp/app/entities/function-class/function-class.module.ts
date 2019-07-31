import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlgorithmdbSharedModule } from 'app/shared';
import {
  FunctionClassComponent,
  FunctionClassDetailComponent,
  FunctionClassUpdateComponent,
  FunctionClassDeletePopupComponent,
  FunctionClassDeleteDialogComponent,
  functionClassRoute,
  functionClassPopupRoute
} from './';

const ENTITY_STATES = [...functionClassRoute, ...functionClassPopupRoute];

@NgModule({
  imports: [AlgorithmdbSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FunctionClassComponent,
    FunctionClassDetailComponent,
    FunctionClassUpdateComponent,
    FunctionClassDeleteDialogComponent,
    FunctionClassDeletePopupComponent
  ],
  entryComponents: [
    FunctionClassComponent,
    FunctionClassUpdateComponent,
    FunctionClassDeleteDialogComponent,
    FunctionClassDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlgorithmdbFunctionClassModule {}
