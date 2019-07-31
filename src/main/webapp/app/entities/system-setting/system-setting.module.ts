import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlgorithmdbSharedModule } from 'app/shared';
import {
  SystemSettingComponent,
  SystemSettingDetailComponent,
  SystemSettingUpdateComponent,
  SystemSettingDeletePopupComponent,
  SystemSettingDeleteDialogComponent,
  systemSettingRoute,
  systemSettingPopupRoute
} from './';

const ENTITY_STATES = [...systemSettingRoute, ...systemSettingPopupRoute];

@NgModule({
  imports: [AlgorithmdbSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SystemSettingComponent,
    SystemSettingDetailComponent,
    SystemSettingUpdateComponent,
    SystemSettingDeleteDialogComponent,
    SystemSettingDeletePopupComponent
  ],
  entryComponents: [
    SystemSettingComponent,
    SystemSettingUpdateComponent,
    SystemSettingDeleteDialogComponent,
    SystemSettingDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlgorithmdbSystemSettingModule {}
