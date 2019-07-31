/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AlgorithmdbTestModule } from '../../../test.module';
import { FunctionClassDetailComponent } from 'app/entities/function-class/function-class-detail.component';
import { FunctionClass } from 'app/shared/model/function-class.model';

describe('Component Tests', () => {
  describe('FunctionClass Management Detail Component', () => {
    let comp: FunctionClassDetailComponent;
    let fixture: ComponentFixture<FunctionClassDetailComponent>;
    const route = ({ data: of({ functionClass: new FunctionClass(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AlgorithmdbTestModule],
        declarations: [FunctionClassDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FunctionClassDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FunctionClassDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.functionClass).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
