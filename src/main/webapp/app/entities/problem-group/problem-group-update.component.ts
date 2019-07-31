import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProblemGroup, ProblemGroup } from 'app/shared/model/problem-group.model';
import { ProblemGroupService } from './problem-group.service';
import { IProblem } from 'app/shared/model/problem.model';
import { ProblemService } from 'app/entities/problem';

@Component({
  selector: 'jhi-problem-group-update',
  templateUrl: './problem-group-update.component.html'
})
export class ProblemGroupUpdateComponent implements OnInit {
  isSaving: boolean;

  problems: IProblem[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(500)]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected problemGroupService: ProblemGroupService,
    protected problemService: ProblemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ problemGroup }) => {
      this.updateForm(problemGroup);
    });
    this.problemService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProblem[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProblem[]>) => response.body)
      )
      .subscribe((res: IProblem[]) => (this.problems = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(problemGroup: IProblemGroup) {
    this.editForm.patchValue({
      id: problemGroup.id,
      name: problemGroup.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const problemGroup = this.createFromForm();
    if (problemGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.problemGroupService.update(problemGroup));
    } else {
      this.subscribeToSaveResponse(this.problemGroupService.create(problemGroup));
    }
  }

  private createFromForm(): IProblemGroup {
    return {
      ...new ProblemGroup(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProblemGroup>>) {
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

  trackProblemById(index: number, item: IProblem) {
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
