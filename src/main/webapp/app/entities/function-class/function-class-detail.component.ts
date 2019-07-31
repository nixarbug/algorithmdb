import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFunctionClass } from 'app/shared/model/function-class.model';

@Component({
  selector: 'jhi-function-class-detail',
  templateUrl: './function-class-detail.component.html'
})
export class FunctionClassDetailComponent implements OnInit {
  functionClass: IFunctionClass;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ functionClass }) => {
      this.functionClass = functionClass;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
