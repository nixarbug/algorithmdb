/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AlgorithmdbTestModule } from '../../../test.module';
import { FunctionClassUpdateComponent } from 'app/entities/function-class/function-class-update.component';
import { FunctionClassService } from 'app/entities/function-class/function-class.service';
import { FunctionClass } from 'app/shared/model/function-class.model';

describe('Component Tests', () => {
  describe('FunctionClass Management Update Component', () => {
    let comp: FunctionClassUpdateComponent;
    let fixture: ComponentFixture<FunctionClassUpdateComponent>;
    let service: FunctionClassService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AlgorithmdbTestModule],
        declarations: [FunctionClassUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FunctionClassUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FunctionClassUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FunctionClassService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FunctionClass(123);
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
        const entity = new FunctionClass();
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
