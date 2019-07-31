import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISystemSetting } from 'app/shared/model/system-setting.model';

@Component({
  selector: 'jhi-system-setting-detail',
  templateUrl: './system-setting-detail.component.html'
})
export class SystemSettingDetailComponent implements OnInit {
  systemSetting: ISystemSetting;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ systemSetting }) => {
      this.systemSetting = systemSetting;
    });
  }

  previousState() {
    window.history.back();
  }
}
