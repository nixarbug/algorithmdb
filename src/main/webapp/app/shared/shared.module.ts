import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AlgorithmdbSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { HighlightService } from 'app/shared/prism-highlight.service';
import { MathService } from 'app/shared/mathjax-math.service';

@NgModule({
  imports: [AlgorithmdbSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [AlgorithmdbSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [HighlightService, MathService]
})
export class AlgorithmdbSharedModule {
  static forRoot() {
    return {
      ngModule: AlgorithmdbSharedModule
    };
  }
}
