import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IImplementation, Implementation } from 'app/shared/model/implementation.model';
import { ImplementationService } from './implementation.service';
import { ILanguage } from 'app/shared/model/language.model';
import { LanguageService } from 'app/entities/language';
import { IAlgorithm } from 'app/shared/model/algorithm.model';
import { AlgorithmService } from 'app/entities/algorithm';

@Component({
  selector: 'jhi-implementation-update',
  templateUrl: './implementation-update.component.html'
})
export class ImplementationUpdateComponent implements OnInit {
  isSaving: boolean;

  languages: ILanguage[];

  algorithms: IAlgorithm[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
    code: [null, [Validators.required]],
    codeMarkdown: [null, [Validators.required]],
    note: [],
    noteMarkdown: [],
    dateCreated: [null, [Validators.required]],
    dateUpdated: [],
    language: [],
    algorithm: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected implementationService: ImplementationService,
    protected languageService: LanguageService,
    protected algorithmService: AlgorithmService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ implementation }) => {
      this.updateForm(implementation);
    });
    this.languageService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILanguage[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILanguage[]>) => response.body)
      )
      .subscribe((res: ILanguage[]) => (this.languages = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.algorithmService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAlgorithm[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAlgorithm[]>) => response.body)
      )
      .subscribe((res: IAlgorithm[]) => (this.algorithms = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(implementation: IImplementation) {
    this.editForm.patchValue({
      id: implementation.id,
      name: implementation.name,
      code: implementation.code,
      codeMarkdown: implementation.codeMarkdown,
      note: implementation.note,
      noteMarkdown: implementation.noteMarkdown,
      dateCreated: implementation.dateCreated != null ? implementation.dateCreated.format(DATE_TIME_FORMAT) : null,
      dateUpdated: implementation.dateUpdated != null ? implementation.dateUpdated.format(DATE_TIME_FORMAT) : null,
      language: implementation.language,
      algorithm: implementation.algorithm
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

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const implementation = this.createFromForm();
    if (implementation.id !== undefined) {
      this.subscribeToSaveResponse(this.implementationService.update(implementation));
    } else {
      this.subscribeToSaveResponse(this.implementationService.create(implementation));
    }
  }

  private createFromForm(): IImplementation {
    return {
      ...new Implementation(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      code: this.editForm.get(['code']).value,
      codeMarkdown: this.editForm.get(['codeMarkdown']).value,
      note: this.editForm.get(['note']).value,
      noteMarkdown: this.editForm.get(['noteMarkdown']).value,
      dateCreated:
        this.editForm.get(['dateCreated']).value != null ? moment(this.editForm.get(['dateCreated']).value, DATE_TIME_FORMAT) : undefined,
      dateUpdated:
        this.editForm.get(['dateUpdated']).value != null ? moment(this.editForm.get(['dateUpdated']).value, DATE_TIME_FORMAT) : undefined,
      language: this.editForm.get(['language']).value,
      algorithm: this.editForm.get(['algorithm']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImplementation>>) {
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

  trackLanguageById(index: number, item: ILanguage) {
    return item.id;
  }

  trackAlgorithmById(index: number, item: IAlgorithm) {
    return item.id;
  }
}
