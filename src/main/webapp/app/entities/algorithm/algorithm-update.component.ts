import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAlgorithm, Algorithm } from 'app/shared/model/algorithm.model';
import { AlgorithmService } from './algorithm.service';
import { IFunctionClass } from 'app/shared/model/function-class.model';
import { FunctionClassService } from 'app/entities/function-class';
import { IAuthor } from 'app/shared/model/author.model';
import { AuthorService } from 'app/entities/author';
import { ITag } from 'app/shared/model/tag.model';
import { TagService } from 'app/entities/tag';
import { IProblem } from 'app/shared/model/problem.model';
import { ProblemService } from 'app/entities/problem';

@Component({
  selector: 'jhi-algorithm-update',
  templateUrl: './algorithm-update.component.html'
})
export class AlgorithmUpdateComponent implements OnInit {
  isSaving: boolean;

  functionclasses: IFunctionClass[];

  authors: IAuthor[];

  tags: ITag[];

  problems: IProblem[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(250)]],
    input: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(500)]],
    output: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(500)]],
    idea: [],
    ideaMarkdown: [],
    description: [],
    descriptionMarkdown: [],
    realLifeUse: [],
    realLifeUseMarkdown: [],
    pseudocode: [],
    pseudocodeMarkdown: [],
    flowchart: [],
    flowchartMarkdown: [],
    flowchartImage: [],
    flowchartImageContentType: [],
    complexityAnalysis: [],
    complexityAnalysisMarkdown: [],
    correctnessProof: [],
    correctnessProofMarkdown: [],
    averageStars: [],
    totalFavs: [],
    weightedRating: [],
    dateCreated: [null, [Validators.required]],
    dateUpdated: [],
    worstCaseComplexity: [],
    averageCaseComplexity: [],
    bestCaseComplexity: [],
    authors: [],
    tags: [],
    problems: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected algorithmService: AlgorithmService,
    protected functionClassService: FunctionClassService,
    protected authorService: AuthorService,
    protected tagService: TagService,
    protected problemService: ProblemService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ algorithm }) => {
      this.updateForm(algorithm);
    });
    this.functionClassService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFunctionClass[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFunctionClass[]>) => response.body)
      )
      .subscribe((res: IFunctionClass[]) => (this.functionclasses = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.authorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAuthor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAuthor[]>) => response.body)
      )
      .subscribe((res: IAuthor[]) => (this.authors = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tagService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITag[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITag[]>) => response.body)
      )
      .subscribe((res: ITag[]) => (this.tags = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.problemService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProblem[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProblem[]>) => response.body)
      )
      .subscribe((res: IProblem[]) => (this.problems = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(algorithm: IAlgorithm) {
    this.editForm.patchValue({
      id: algorithm.id,
      name: algorithm.name,
      input: algorithm.input,
      output: algorithm.output,
      idea: algorithm.idea,
      ideaMarkdown: algorithm.ideaMarkdown,
      description: algorithm.description,
      descriptionMarkdown: algorithm.descriptionMarkdown,
      realLifeUse: algorithm.realLifeUse,
      realLifeUseMarkdown: algorithm.realLifeUseMarkdown,
      pseudocode: algorithm.pseudocode,
      pseudocodeMarkdown: algorithm.pseudocodeMarkdown,
      flowchart: algorithm.flowchart,
      flowchartMarkdown: algorithm.flowchartMarkdown,
      flowchartImage: algorithm.flowchartImage,
      flowchartImageContentType: algorithm.flowchartImageContentType,
      complexityAnalysis: algorithm.complexityAnalysis,
      complexityAnalysisMarkdown: algorithm.complexityAnalysisMarkdown,
      correctnessProof: algorithm.correctnessProof,
      correctnessProofMarkdown: algorithm.correctnessProofMarkdown,
      averageStars: algorithm.averageStars,
      totalFavs: algorithm.totalFavs,
      weightedRating: algorithm.weightedRating,
      dateCreated: algorithm.dateCreated != null ? algorithm.dateCreated.format(DATE_TIME_FORMAT) : null,
      dateUpdated: algorithm.dateUpdated != null ? algorithm.dateUpdated.format(DATE_TIME_FORMAT) : null,
      worstCaseComplexity: algorithm.worstCaseComplexity,
      averageCaseComplexity: algorithm.averageCaseComplexity,
      bestCaseComplexity: algorithm.bestCaseComplexity,
      authors: algorithm.authors,
      tags: algorithm.tags,
      problems: algorithm.problems
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const algorithm = this.createFromForm();
    if (algorithm.id !== undefined) {
      this.subscribeToSaveResponse(this.algorithmService.update(algorithm));
    } else {
      this.subscribeToSaveResponse(this.algorithmService.create(algorithm));
    }
  }

  private createFromForm(): IAlgorithm {
    return {
      ...new Algorithm(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      input: this.editForm.get(['input']).value,
      output: this.editForm.get(['output']).value,
      idea: this.editForm.get(['idea']).value,
      ideaMarkdown: this.editForm.get(['ideaMarkdown']).value,
      description: this.editForm.get(['description']).value,
      descriptionMarkdown: this.editForm.get(['descriptionMarkdown']).value,
      realLifeUse: this.editForm.get(['realLifeUse']).value,
      realLifeUseMarkdown: this.editForm.get(['realLifeUseMarkdown']).value,
      pseudocode: this.editForm.get(['pseudocode']).value,
      pseudocodeMarkdown: this.editForm.get(['pseudocodeMarkdown']).value,
      flowchart: this.editForm.get(['flowchart']).value,
      flowchartMarkdown: this.editForm.get(['flowchartMarkdown']).value,
      flowchartImageContentType: this.editForm.get(['flowchartImageContentType']).value,
      flowchartImage: this.editForm.get(['flowchartImage']).value,
      complexityAnalysis: this.editForm.get(['complexityAnalysis']).value,
      complexityAnalysisMarkdown: this.editForm.get(['complexityAnalysisMarkdown']).value,
      correctnessProof: this.editForm.get(['correctnessProof']).value,
      correctnessProofMarkdown: this.editForm.get(['correctnessProofMarkdown']).value,
      averageStars: this.editForm.get(['averageStars']).value,
      totalFavs: this.editForm.get(['totalFavs']).value,
      weightedRating: this.editForm.get(['weightedRating']).value,
      dateCreated:
        this.editForm.get(['dateCreated']).value != null ? moment(this.editForm.get(['dateCreated']).value, DATE_TIME_FORMAT) : undefined,
      dateUpdated:
        this.editForm.get(['dateUpdated']).value != null ? moment(this.editForm.get(['dateUpdated']).value, DATE_TIME_FORMAT) : undefined,
      worstCaseComplexity: this.editForm.get(['worstCaseComplexity']).value,
      averageCaseComplexity: this.editForm.get(['averageCaseComplexity']).value,
      bestCaseComplexity: this.editForm.get(['bestCaseComplexity']).value,
      authors: this.editForm.get(['authors']).value,
      tags: this.editForm.get(['tags']).value,
      problems: this.editForm.get(['problems']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlgorithm>>) {
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

  trackFunctionClassById(index: number, item: IFunctionClass) {
    return item.id;
  }

  trackAuthorById(index: number, item: IAuthor) {
    return item.id;
  }

  trackTagById(index: number, item: ITag) {
    return item.id;
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
