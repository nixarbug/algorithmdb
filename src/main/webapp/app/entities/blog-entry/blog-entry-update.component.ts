import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IBlogEntry, BlogEntry } from 'app/shared/model/blog-entry.model';
import { BlogEntryService } from './blog-entry.service';
import { ITag } from 'app/shared/model/tag.model';
import { TagService } from 'app/entities/tag';
import { IBlog } from 'app/shared/model/blog.model';
import { BlogService } from 'app/entities/blog';

@Component({
  selector: 'jhi-blog-entry-update',
  templateUrl: './blog-entry-update.component.html'
})
export class BlogEntryUpdateComponent implements OnInit {
  isSaving: boolean;

  tags: ITag[];

  blogs: IBlog[];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
    content: [],
    dateCreated: [null, [Validators.required]],
    dateUpdated: [],
    tags: [],
    blog: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected blogEntryService: BlogEntryService,
    protected tagService: TagService,
    protected blogService: BlogService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ blogEntry }) => {
      this.updateForm(blogEntry);
    });
    this.tagService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITag[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITag[]>) => response.body)
      )
      .subscribe((res: ITag[]) => (this.tags = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.blogService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBlog[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBlog[]>) => response.body)
      )
      .subscribe((res: IBlog[]) => (this.blogs = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(blogEntry: IBlogEntry) {
    this.editForm.patchValue({
      id: blogEntry.id,
      title: blogEntry.title,
      content: blogEntry.content,
      dateCreated: blogEntry.dateCreated != null ? blogEntry.dateCreated.format(DATE_TIME_FORMAT) : null,
      dateUpdated: blogEntry.dateUpdated != null ? blogEntry.dateUpdated.format(DATE_TIME_FORMAT) : null,
      tags: blogEntry.tags,
      blog: blogEntry.blog
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
    const blogEntry = this.createFromForm();
    if (blogEntry.id !== undefined) {
      this.subscribeToSaveResponse(this.blogEntryService.update(blogEntry));
    } else {
      this.subscribeToSaveResponse(this.blogEntryService.create(blogEntry));
    }
  }

  private createFromForm(): IBlogEntry {
    return {
      ...new BlogEntry(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      content: this.editForm.get(['content']).value,
      dateCreated:
        this.editForm.get(['dateCreated']).value != null ? moment(this.editForm.get(['dateCreated']).value, DATE_TIME_FORMAT) : undefined,
      dateUpdated:
        this.editForm.get(['dateUpdated']).value != null ? moment(this.editForm.get(['dateUpdated']).value, DATE_TIME_FORMAT) : undefined,
      tags: this.editForm.get(['tags']).value,
      blog: this.editForm.get(['blog']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBlogEntry>>) {
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

  trackTagById(index: number, item: ITag) {
    return item.id;
  }

  trackBlogById(index: number, item: IBlog) {
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
