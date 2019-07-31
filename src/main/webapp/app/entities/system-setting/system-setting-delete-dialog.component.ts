import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISystemSetting } from 'app/shared/model/system-setting.model';
import { SystemSettingService } from './system-setting.service';

@Component({
  selector: 'jhi-system-setting-delete-dialog',
  templateUrl: './system-setting-delete-dialog.component.html'
})
export class SystemSettingDeleteDialogComponent {
  systemSetting: ISystemSetting;

  constructor(
    protected systemSettingService: SystemSettingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.systemSettingService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'systemSettingListModification',
        content: 'Deleted an systemSetting'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-system-setting-delete-popup',
  template: ''
})
export class SystemSettingDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ systemSetting }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SystemSettingDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.systemSetting = systemSetting;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/system-setting', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/system-setting', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
