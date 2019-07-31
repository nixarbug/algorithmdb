/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AlgorithmService } from 'app/entities/algorithm/algorithm.service';
import { IAlgorithm, Algorithm } from 'app/shared/model/algorithm.model';

describe('Service Tests', () => {
  describe('Algorithm Service', () => {
    let injector: TestBed;
    let service: AlgorithmService;
    let httpMock: HttpTestingController;
    let elemDefault: IAlgorithm;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AlgorithmService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Algorithm(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dateCreated: currentDate.format(DATE_TIME_FORMAT),
            dateUpdated: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Algorithm', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateCreated: currentDate.format(DATE_TIME_FORMAT),
            dateUpdated: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateCreated: currentDate,
            dateUpdated: currentDate
          },
          returnedFromService
        );
        service
          .create(new Algorithm(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Algorithm', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            input: 'BBBBBB',
            output: 'BBBBBB',
            idea: 'BBBBBB',
            description: 'BBBBBB',
            realLifeUse: 'BBBBBB',
            pseudocode: 'BBBBBB',
            flowchart: 'BBBBBB',
            flowchartImage: 'BBBBBB',
            complexityAnalysis: 'BBBBBB',
            correctnessProof: 'BBBBBB',
            averageStars: 1,
            totalFavs: 1,
            weightedRating: 1,
            dateCreated: currentDate.format(DATE_TIME_FORMAT),
            dateUpdated: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreated: currentDate,
            dateUpdated: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Algorithm', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            input: 'BBBBBB',
            output: 'BBBBBB',
            idea: 'BBBBBB',
            description: 'BBBBBB',
            realLifeUse: 'BBBBBB',
            pseudocode: 'BBBBBB',
            flowchart: 'BBBBBB',
            flowchartImage: 'BBBBBB',
            complexityAnalysis: 'BBBBBB',
            correctnessProof: 'BBBBBB',
            averageStars: 1,
            totalFavs: 1,
            weightedRating: 1,
            dateCreated: currentDate.format(DATE_TIME_FORMAT),
            dateUpdated: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateCreated: currentDate,
            dateUpdated: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Algorithm', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
