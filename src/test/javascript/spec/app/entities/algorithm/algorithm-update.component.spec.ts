/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AlgorithmdbTestModule } from '../../../test.module';
import { AlgorithmUpdateComponent } from 'app/entities/algorithm/algorithm-update.component';
import { AlgorithmService } from 'app/entities/algorithm/algorithm.service';
import { Algorithm } from 'app/shared/model/algorithm.model';

describe('Component Tests', () => {
  describe('Algorithm Management Update Component', () => {
    let comp: AlgorithmUpdateComponent;
    let fixture: ComponentFixture<AlgorithmUpdateComponent>;
    let service: AlgorithmService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AlgorithmdbTestModule],
        declarations: [AlgorithmUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AlgorithmUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlgorithmUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlgorithmService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Algorithm(123);
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
        const entity = new Algorithm();
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
