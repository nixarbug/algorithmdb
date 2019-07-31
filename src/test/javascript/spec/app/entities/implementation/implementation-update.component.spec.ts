/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AlgorithmdbTestModule } from '../../../test.module';
import { ImplementationUpdateComponent } from 'app/entities/implementation/implementation-update.component';
import { ImplementationService } from 'app/entities/implementation/implementation.service';
import { Implementation } from 'app/shared/model/implementation.model';

describe('Component Tests', () => {
  describe('Implementation Management Update Component', () => {
    let comp: ImplementationUpdateComponent;
    let fixture: ComponentFixture<ImplementationUpdateComponent>;
    let service: ImplementationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AlgorithmdbTestModule],
        declarations: [ImplementationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ImplementationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImplementationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImplementationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Implementation(123);
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
        const entity = new Implementation();
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
