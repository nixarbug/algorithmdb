import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AlgorithmdbSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [AlgorithmdbSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [AlgorithmdbSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlgorithmdbSharedModule {
  static forRoot() {
    return {
      ngModule: AlgorithmdbSharedModule
    };
  }
}
