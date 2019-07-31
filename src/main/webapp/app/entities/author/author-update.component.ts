import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAuthor, Author } from 'app/shared/model/author.model';
import { AuthorService } from './author.service';
import { IAlgorithm } from 'app/shared/model/algorithm.model';
import { AlgorithmService } from 'app/entities/algorithm';

@Component({
  selector: 'jhi-author-update',
  templateUrl: './author-update.component.html'
})
export class AuthorUpdateComponent implements OnInit {
  isSaving: boolean;

  algorithms: IAlgorithm[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
    picture: [],
    pictureContentType: [],
    info: [],
    dateCreated: [null, [Validators.required]],
    dateUpdated: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected authorService: AuthorService,
    protected algorithmService: AlgorithmService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ author }) => {
      this.updateForm(author);
    });
    this.algorithmService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAlgorithm[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAlgorithm[]>) => response.body)
      )
      .subscribe((res: IAlgorithm[]) => (this.algorithms = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(author: IAuthor) {
    this.editForm.patchValue({
      id: author.id,
      name: author.name,
      picture: author.picture,
      pictureContentType: author.pictureContentType,
      info: author.info,
      dateCreated: author.dateCreated != null ? author.dateCreated.format(DATE_TIME_FORMAT) : null,
      dateUpdated: author.dateUpdated != null ? author.dateUpdated.format(DATE_TIME_FORMAT) : null
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
    const author = this.createFromForm();
    if (author.id !== undefined) {
      this.subscribeToSaveResponse(this.authorService.update(author));
    } else {
      this.subscribeToSaveResponse(this.authorService.create(author));
    }
  }

  private createFromForm(): IAuthor {
    return {
      ...new Author(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      pictureContentType: this.editForm.get(['pictureContentType']).value,
      picture: this.editForm.get(['picture']).value,
      info: this.editForm.get(['info']).value,
      dateCreated:
        this.editForm.get(['dateCreated']).value != null ? moment(this.editForm.get(['dateCreated']).value, DATE_TIME_FORMAT) : undefined,
      dateUpdated:
        this.editForm.get(['dateUpdated']).value != null ? moment(this.editForm.get(['dateUpdated']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuthor>>) {
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
