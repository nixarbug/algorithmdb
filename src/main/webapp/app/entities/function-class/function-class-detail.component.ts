import { Component, OnInit, AfterViewChecked } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFunctionClass } from 'app/shared/model/function-class.model';
import { HighlightService } from '../../shared/prism-highlight.service';
import { MathService } from '../../shared/mathjax-math.service';

@Component({
  selector: 'jhi-function-class-detail',
  templateUrl: './function-class-detail.component.html'
})
export class FunctionClassDetailComponent implements OnInit, AfterViewChecked {
  functionClass: IFunctionClass;
  mathProcessedFormula;
  highlighted = false;
  mathProcessed = false;
  constructor(
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    protected highlighter: HighlightService,
    protected mathProcessor: MathService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ functionClass }) => {
      this.functionClass = functionClass;
      this.mathProcessedFormula = functionClass.formula;
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

  ngAfterViewChecked() {
    if (this.functionClass) {
      if (!this.highlighted) {
        this.highlighter.highlightAll();
        this.highlighted = true;
      }
      if (!this.mathProcessed) {
        this.mathProcessor.processMath();
      }
    }
  }
}
