import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFunctionClass } from 'app/shared/model/function-class.model';
import { FunctionClassService } from './function-class.service';

@Component({
  selector: 'jhi-function-class-delete-dialog',
  templateUrl: './function-class-delete-dialog.component.html'
})
export class FunctionClassDeleteDialogComponent {
  functionClass: IFunctionClass;

  constructor(
    protected functionClassService: FunctionClassService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.functionClassService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'functionClassListModification',
        content: 'Deleted an functionClass'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-function-class-delete-popup',
  template: ''
})
export class FunctionClassDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ functionClass }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FunctionClassDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.functionClass = functionClass;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/function-class', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/function-class', { outlets: { popup: null } }]);
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
