/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AlgorithmdbTestModule } from '../../../test.module';
import { SystemSettingDetailComponent } from 'app/entities/system-setting/system-setting-detail.component';
import { SystemSetting } from 'app/shared/model/system-setting.model';

describe('Component Tests', () => {
  describe('SystemSetting Management Detail Component', () => {
    let comp: SystemSettingDetailComponent;
    let fixture: ComponentFixture<SystemSettingDetailComponent>;
    const route = ({ data: of({ systemSetting: new SystemSetting(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AlgorithmdbTestModule],
        declarations: [SystemSettingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SystemSettingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SystemSettingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.systemSetting).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
