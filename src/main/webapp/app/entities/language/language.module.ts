import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlgorithmdbSharedModule } from 'app/shared';
import {
  LanguageComponent,
  LanguageDetailComponent,
  LanguageUpdateComponent,
  LanguageDeletePopupComponent,
  LanguageDeleteDialogComponent,
  languageRoute,
  languagePopupRoute
} from './';

const ENTITY_STATES = [...languageRoute, ...languagePopupRoute];

@NgModule({
  imports: [AlgorithmdbSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LanguageComponent,
    LanguageDetailComponent,
    LanguageUpdateComponent,
    LanguageDeleteDialogComponent,
    LanguageDeletePopupComponent
  ],
  entryComponents: [LanguageComponent, LanguageUpdateComponent, LanguageDeleteDialogComponent, LanguageDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlgorithmdbLanguageModule {}
