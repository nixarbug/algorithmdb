/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AlgorithmdbTestModule } from '../../../test.module';
import { ImplementationDetailComponent } from 'app/entities/implementation/implementation-detail.component';
import { Implementation } from 'app/shared/model/implementation.model';

describe('Component Tests', () => {
  describe('Implementation Management Detail Component', () => {
    let comp: ImplementationDetailComponent;
    let fixture: ComponentFixture<ImplementationDetailComponent>;
    const route = ({ data: of({ implementation: new Implementation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AlgorithmdbTestModule],
        declarations: [ImplementationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ImplementationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImplementationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.implementation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
