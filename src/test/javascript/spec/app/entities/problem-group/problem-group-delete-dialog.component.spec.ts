/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AlgorithmdbTestModule } from '../../../test.module';
import { ProblemGroupDeleteDialogComponent } from 'app/entities/problem-group/problem-group-delete-dialog.component';
import { ProblemGroupService } from 'app/entities/problem-group/problem-group.service';

describe('Component Tests', () => {
  describe('ProblemGroup Management Delete Component', () => {
    let comp: ProblemGroupDeleteDialogComponent;
    let fixture: ComponentFixture<ProblemGroupDeleteDialogComponent>;
    let service: ProblemGroupService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AlgorithmdbTestModule],
        declarations: [ProblemGroupDeleteDialogComponent]
      })
        .overrideTemplate(ProblemGroupDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProblemGroupDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProblemGroupService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
