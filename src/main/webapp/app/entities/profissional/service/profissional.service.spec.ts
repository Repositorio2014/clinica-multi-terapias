import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IProfissional } from '../profissional.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../profissional.test-samples';

import { ProfissionalService } from './profissional.service';

const requireRestSample: IProfissional = {
  ...sampleWithRequiredData,
};

describe('Profissional Service', () => {
  let service: ProfissionalService;
  let httpMock: HttpTestingController;
  let expectedResult: IProfissional | IProfissional[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ProfissionalService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Profissional', () => {
      const profissional = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(profissional).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Profissional', () => {
      const profissional = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(profissional).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Profissional', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Profissional', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Profissional', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProfissionalToCollectionIfMissing', () => {
      it('should add a Profissional to an empty array', () => {
        const profissional: IProfissional = sampleWithRequiredData;
        expectedResult = service.addProfissionalToCollectionIfMissing([], profissional);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(profissional);
      });

      it('should not add a Profissional to an array that contains it', () => {
        const profissional: IProfissional = sampleWithRequiredData;
        const profissionalCollection: IProfissional[] = [
          {
            ...profissional,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProfissionalToCollectionIfMissing(profissionalCollection, profissional);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Profissional to an array that doesn't contain it", () => {
        const profissional: IProfissional = sampleWithRequiredData;
        const profissionalCollection: IProfissional[] = [sampleWithPartialData];
        expectedResult = service.addProfissionalToCollectionIfMissing(profissionalCollection, profissional);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(profissional);
      });

      it('should add only unique Profissional to an array', () => {
        const profissionalArray: IProfissional[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const profissionalCollection: IProfissional[] = [sampleWithRequiredData];
        expectedResult = service.addProfissionalToCollectionIfMissing(profissionalCollection, ...profissionalArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const profissional: IProfissional = sampleWithRequiredData;
        const profissional2: IProfissional = sampleWithPartialData;
        expectedResult = service.addProfissionalToCollectionIfMissing([], profissional, profissional2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(profissional);
        expect(expectedResult).toContain(profissional2);
      });

      it('should accept null and undefined values', () => {
        const profissional: IProfissional = sampleWithRequiredData;
        expectedResult = service.addProfissionalToCollectionIfMissing([], null, profissional, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(profissional);
      });

      it('should return initial array if no Profissional is added', () => {
        const profissionalCollection: IProfissional[] = [sampleWithRequiredData];
        expectedResult = service.addProfissionalToCollectionIfMissing(profissionalCollection, undefined, null);
        expect(expectedResult).toEqual(profissionalCollection);
      });
    });

    describe('compareProfissional', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProfissional(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProfissional(entity1, entity2);
        const compareResult2 = service.compareProfissional(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProfissional(entity1, entity2);
        const compareResult2 = service.compareProfissional(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProfissional(entity1, entity2);
        const compareResult2 = service.compareProfissional(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
