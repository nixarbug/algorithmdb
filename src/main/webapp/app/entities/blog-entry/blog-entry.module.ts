import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AlgorithmdbSharedModule } from 'app/shared';
import {
  BlogEntryComponent,
  BlogEntryDetailComponent,
  BlogEntryUpdateComponent,
  BlogEntryDeletePopupComponent,
  BlogEntryDeleteDialogComponent,
  blogEntryRoute,
  blogEntryPopupRoute
} from './';

const ENTITY_STATES = [...blogEntryRoute, ...blogEntryPopupRoute];

@NgModule({
  imports: [AlgorithmdbSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BlogEntryComponent,
    BlogEntryDetailComponent,
    BlogEntryUpdateComponent,
    BlogEntryDeleteDialogComponent,
    BlogEntryDeletePopupComponent
  ],
  entryComponents: [BlogEntryComponent, BlogEntryUpdateComponent, BlogEntryDeleteDialogComponent, BlogEntryDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlgorithmdbBlogEntryModule {}
