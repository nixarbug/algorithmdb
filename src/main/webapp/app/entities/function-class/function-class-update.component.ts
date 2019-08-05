import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IFunctionClass, FunctionClass } from 'app/shared/model/function-class.model';
import { FunctionClassService } from './function-class.service';

@Component({
  selector: 'jhi-function-class-update',
  templateUrl: './function-class-update.component.html'
})
export class FunctionClassUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    formula: [null, [Validators.required]],
    formulaMarkdown: [null, [Validators.required]],
    relativeOrder: [null, [Validators.required]]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected functionClassService: FunctionClassService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ functionClass }) => {
      this.updateForm(functionClass);
    });
  }

  updateForm(functionClass: IFunctionClass) {
    this.editForm.patchValue({
      id: functionClass.id,
      name: functionClass.name,
      formula: functionClass.formula,
      formulaMarkdown: functionClass.formulaMarkdown,
      relativeOrder: functionClass.relativeOrder
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
    const functionClass = this.createFromForm();
    if (functionClass.id !== undefined) {
      this.subscribeToSaveResponse(this.functionClassService.update(functionClass));
    } else {
      this.subscribeToSaveResponse(this.functionClassService.create(functionClass));
    }
  }

  private createFromForm(): IFunctionClass {
    return {
      ...new FunctionClass(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      formula: this.editForm.get(['formula']).value,
      formulaMarkdown: this.editForm.get(['formulaMarkdown']).value,
      relativeOrder: this.editForm.get(['relativeOrder']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFunctionClass>>) {
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
}
