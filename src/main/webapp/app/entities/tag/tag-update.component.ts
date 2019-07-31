import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITag, Tag } from 'app/shared/model/tag.model';
import { TagService } from './tag.service';
import { IAlgorithm } from 'app/shared/model/algorithm.model';
import { AlgorithmService } from 'app/entities/algorithm';
import { IBlogEntry } from 'app/shared/model/blog-entry.model';
import { BlogEntryService } from 'app/entities/blog-entry';

@Component({
  selector: 'jhi-tag-update',
  templateUrl: './tag-update.component.html'
})
export class TagUpdateComponent implements OnInit {
  isSaving: boolean;

  algorithms: IAlgorithm[];

  blogentries: IBlogEntry[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(500)]],
    type: [null, [Validators.required]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tagService: TagService,
    protected algorithmService: AlgorithmService,
    protected blogEntryService: BlogEntryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tag }) => {
      this.updateForm(tag);
    });
    this.algorithmService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAlgorithm[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAlgorithm[]>) => response.body)
      )
      .subscribe((res: IAlgorithm[]) => (this.algorithms = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.blogEntryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBlogEntry[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBlogEntry[]>) => response.body)
      )
      .subscribe((res: IBlogEntry[]) => (this.blogentries = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tag: ITag) {
    this.editForm.patchValue({
      id: tag.id,
      name: tag.name,
      type: tag.type
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tag = this.createFromForm();
    if (tag.id !== undefined) {
      this.subscribeToSaveResponse(this.tagService.update(tag));
    } else {
      this.subscribeToSaveResponse(this.tagService.create(tag));
    }
  }

  private createFromForm(): ITag {
    return {
      ...new Tag(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      type: this.editForm.get(['type']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITag>>) {
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

  trackBlogEntryById(index: number, item: IBlogEntry) {
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
