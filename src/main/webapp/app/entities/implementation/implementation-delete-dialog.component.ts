import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IImplementation } from 'app/shared/model/implementation.model';
import { ImplementationService } from './implementation.service';

@Component({
  selector: 'jhi-implementation-delete-dialog',
  templateUrl: './implementation-delete-dialog.component.html'
})
export class ImplementationDeleteDialogComponent {
  implementation: IImplementation;

  constructor(
    protected implementationService: ImplementationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.implementationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'implementationListModification',
        content: 'Deleted an implementation'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-implementation-delete-popup',
  template: ''
})
export class ImplementationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ implementation }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ImplementationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.implementation = implementation;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/implementation', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/implementation', { outlets: { popup: null } }]);
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
