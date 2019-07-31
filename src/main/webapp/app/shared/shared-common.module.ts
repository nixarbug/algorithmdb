import { NgModule } from '@angular/core';

import { AlgorithmdbSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [AlgorithmdbSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [AlgorithmdbSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class AlgorithmdbSharedCommonModule {}
