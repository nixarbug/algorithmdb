import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProblemGroup } from 'app/shared/model/problem-group.model';
import { ProblemGroupService } from './problem-group.service';

@Component({
  selector: 'jhi-problem-group-delete-dialog',
  templateUrl: './problem-group-delete-dialog.component.html'
})
export class ProblemGroupDeleteDialogComponent {
  problemGroup: IProblemGroup;

  constructor(
    protected problemGroupService: ProblemGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.problemGroupService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'problemGroupListModification',
        content: 'Deleted an problemGroup'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-problem-group-delete-popup',
  template: ''
})
export class ProblemGroupDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ problemGroup }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProblemGroupDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.problemGroup = problemGroup;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/problem-group', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/problem-group', { outlets: { popup: null } }]);
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
