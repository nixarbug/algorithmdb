/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AlgorithmdbTestModule } from '../../../test.module';
import { ProblemGroupUpdateComponent } from 'app/entities/problem-group/problem-group-update.component';
import { ProblemGroupService } from 'app/entities/problem-group/problem-group.service';
import { ProblemGroup } from 'app/shared/model/problem-group.model';

describe('Component Tests', () => {
  describe('ProblemGroup Management Update Component', () => {
    let comp: ProblemGroupUpdateComponent;
    let fixture: ComponentFixture<ProblemGroupUpdateComponent>;
    let service: ProblemGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AlgorithmdbTestModule],
        declarations: [ProblemGroupUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProblemGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProblemGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProblemGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProblemGroup(123);
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
        const entity = new ProblemGroup();
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
