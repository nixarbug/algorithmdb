import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISystemSetting, SystemSetting } from 'app/shared/model/system-setting.model';
import { SystemSettingService } from './system-setting.service';

@Component({
  selector: 'jhi-system-setting-update',
  templateUrl: './system-setting-update.component.html'
})
export class SystemSettingUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required]],
    value: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
    description: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(500)]]
  });

  constructor(protected systemSettingService: SystemSettingService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ systemSetting }) => {
      this.updateForm(systemSetting);
    });
  }

  updateForm(systemSetting: ISystemSetting) {
    this.editForm.patchValue({
      id: systemSetting.id,
      key: systemSetting.key,
      value: systemSetting.value,
      description: systemSetting.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const systemSetting = this.createFromForm();
    if (systemSetting.id !== undefined) {
      this.subscribeToSaveResponse(this.systemSettingService.update(systemSetting));
    } else {
      this.subscribeToSaveResponse(this.systemSettingService.create(systemSetting));
    }
  }

  private createFromForm(): ISystemSetting {
    return {
      ...new SystemSetting(),
      id: this.editForm.get(['id']).value,
      key: this.editForm.get(['key']).value,
      value: this.editForm.get(['value']).value,
      description: this.editForm.get(['description']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISystemSetting>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
