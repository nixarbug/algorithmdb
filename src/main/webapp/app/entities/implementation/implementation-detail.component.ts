import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IImplementation } from 'app/shared/model/implementation.model';

@Component({
  selector: 'jhi-implementation-detail',
  templateUrl: './implementation-detail.component.html'
})
export class ImplementationDetailComponent implements OnInit {
  implementation: IImplementation;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ implementation }) => {
      this.implementation = implementation;
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
