/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AlgorithmdbTestModule } from '../../../test.module';
import { SystemSettingDeleteDialogComponent } from 'app/entities/system-setting/system-setting-delete-dialog.component';
import { SystemSettingService } from 'app/entities/system-setting/system-setting.service';

describe('Component Tests', () => {
  describe('SystemSetting Management Delete Component', () => {
    let comp: SystemSettingDeleteDialogComponent;
    let fixture: ComponentFixture<SystemSettingDeleteDialogComponent>;
    let service: SystemSettingService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AlgorithmdbTestModule],
        declarations: [SystemSettingDeleteDialogComponent]
      })
        .overrideTemplate(SystemSettingDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SystemSettingDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SystemSettingService);
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
