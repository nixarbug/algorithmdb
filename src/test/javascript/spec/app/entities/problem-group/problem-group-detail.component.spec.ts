/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AlgorithmdbTestModule } from '../../../test.module';
import { ProblemGroupDetailComponent } from 'app/entities/problem-group/problem-group-detail.component';
import { ProblemGroup } from 'app/shared/model/problem-group.model';

describe('Component Tests', () => {
  describe('ProblemGroup Management Detail Component', () => {
    let comp: ProblemGroupDetailComponent;
    let fixture: ComponentFixture<ProblemGroupDetailComponent>;
    const route = ({ data: of({ problemGroup: new ProblemGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AlgorithmdbTestModule],
        declarations: [ProblemGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProblemGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProblemGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.problemGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
