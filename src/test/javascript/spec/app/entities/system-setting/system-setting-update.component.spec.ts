/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AlgorithmdbTestModule } from '../../../test.module';
import { SystemSettingUpdateComponent } from 'app/entities/system-setting/system-setting-update.component';
import { SystemSettingService } from 'app/entities/system-setting/system-setting.service';
import { SystemSetting } from 'app/shared/model/system-setting.model';

describe('Component Tests', () => {
  describe('SystemSetting Management Update Component', () => {
    let comp: SystemSettingUpdateComponent;
    let fixture: ComponentFixture<SystemSettingUpdateComponent>;
    let service: SystemSettingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AlgorithmdbTestModule],
        declarations: [SystemSettingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SystemSettingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SystemSettingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SystemSettingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SystemSetting(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SystemSetting();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
