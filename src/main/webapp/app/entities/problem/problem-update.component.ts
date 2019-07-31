import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IProblem, Problem } from 'app/shared/model/problem.model';
import { ProblemService } from './problem.service';
import { IProblemGroup } from 'app/shared/model/problem-group.model';
import { ProblemGroupService } from 'app/entities/problem-group';
import { IAlgorithm } from 'app/shared/model/algorithm.model';
import { AlgorithmService } from 'app/entities/algorithm';

@Component({
  selector: 'jhi-problem-update',
  templateUrl: './problem-update.component.html'
})
export class ProblemUpdateComponent implements OnInit {
  isSaving: boolean;

  problemgroups: IProblemGroup[];

  algorithms: IAlgorithm[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(500)]],
    description: [null, [Validators.required, Validators.maxLength(1000)]],
    dateCreated: [null, [Validators.required]],
    dateUpdated: [],
    problemGroups: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected problemService: ProblemService,
    protected problemGroupService: ProblemGroupService,
    protected algorithmService: AlgorithmService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ problem }) => {
      this.updateForm(problem);
    });
    this.problemGroupService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProblemGroup[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProblemGroup[]>) => response.body)
      )
      .subscribe((res: IProblemGroup[]) => (this.problemgroups = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.algorithmService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAlgorithm[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAlgorithm[]>) => response.body)
      )
      .subscribe((res: IAlgorithm[]) => (this.algorithms = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(problem: IProblem) {
    this.editForm.patchValue({
      id: problem.id,
      name: problem.name,
      description: problem.description,
      dateCreated: problem.dateCreated != null ? problem.dateCreated.format(DATE_TIME_FORMAT) : null,
      dateUpdated: problem.dateUpdated != null ? problem.dateUpdated.format(DATE_TIME_FORMAT) : null,
      problemGroups: problem.problemGroups
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const problem = this.createFromForm();
    if (problem.id !== undefined) {
      this.subscribeToSaveResponse(this.problemService.update(problem));
    } else {
      this.subscribeToSaveResponse(this.problemService.create(problem));
    }
  }

  private createFromForm(): IProblem {
    return {
      ...new Problem(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      dateCreated:
        this.editForm.get(['dateCreated']).value != null ? moment(this.editForm.get(['dateCreated']).value, DATE_TIME_FORMAT) : undefined,
      dateUpdated:
        this.editForm.get(['dateUpdated']).value != null ? moment(this.editForm.get(['dateUpdated']).value, DATE_TIME_FORMAT) : undefined,
      problemGroups: this.editForm.get(['problemGroups']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProblem>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProblemGroupById(index: number, item: IProblemGroup) {
    return item.id;
  }

  trackAlgorithmById(index: number, item: IAlgorithm) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
