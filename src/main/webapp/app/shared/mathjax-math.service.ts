import { Injectable, Inject } from '@angular/core';

import { PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

declare var MathJax: any;

import 'clipboard';

@Injectable()
export class MathService {
  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  processMath() {
    if (isPlatformBrowser(this.platformId)) {
      MathJax.Hub.Queue(['Typeset', MathJax.Hub]);
    }
  }
}
