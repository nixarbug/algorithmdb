import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProblemGroup } from 'app/shared/model/problem-group.model';

@Component({
  selector: 'jhi-problem-group-detail',
  templateUrl: './problem-group-detail.component.html'
})
export class ProblemGroupDetailComponent implements OnInit {
  problemGroup: IProblemGroup;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ problemGroup }) => {
      this.problemGroup = problemGroup;
    });
  }

  previousState() {
    window.history.back();
  }
}
