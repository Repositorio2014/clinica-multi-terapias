import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IProntuario } from '../prontuario.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../prontuario.test-samples';

import { ProntuarioService, RestProntuario } from './prontuario.service';

const requireRestSample: RestProntuario = {
  ...sampleWithRequiredData,
  dataAtendimento: sampleWithRequiredData.dataAtendimento?.toJSON(),
};

describe('Prontuario Service', () => {
  let service: ProntuarioService;
  let httpMock: HttpTestingController;
  let expectedResult: IProntuario | IProntuario[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ProntuarioService);
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

    it('should create a Prontuario', () => {
      const prontuario = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(prontuario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Prontuario', () => {
      const prontuario = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(prontuario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Prontuario', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Prontuario', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Prontuario', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProntuarioToCollectionIfMissing', () => {
      it('should add a Prontuario to an empty array', () => {
        const prontuario: IProntuario = sampleWithRequiredData;
        expectedResult = service.addProntuarioToCollectionIfMissing([], prontuario);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(prontuario);
      });

      it('should not add a Prontuario to an array that contains it', () => {
        const prontuario: IProntuario = sampleWithRequiredData;
        const prontuarioCollection: IProntuario[] = [
          {
            ...prontuario,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProntuarioToCollectionIfMissing(prontuarioCollection, prontuario);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Prontuario to an array that doesn't contain it", () => {
        const prontuario: IProntuario = sampleWithRequiredData;
        const prontuarioCollection: IProntuario[] = [sampleWithPartialData];
        expectedResult = service.addProntuarioToCollectionIfMissing(prontuarioCollection, prontuario);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(prontuario);
      });

      it('should add only unique Prontuario to an array', () => {
        const prontuarioArray: IProntuario[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const prontuarioCollection: IProntuario[] = [sampleWithRequiredData];
        expectedResult = service.addProntuarioToCollectionIfMissing(prontuarioCollection, ...prontuarioArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const prontuario: IProntuario = sampleWithRequiredData;
        const prontuario2: IProntuario = sampleWithPartialData;
        expectedResult = service.addProntuarioToCollectionIfMissing([], prontuario, prontuario2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(prontuario);
        expect(expectedResult).toContain(prontuario2);
      });

      it('should accept null and undefined values', () => {
        const prontuario: IProntuario = sampleWithRequiredData;
        expectedResult = service.addProntuarioToCollectionIfMissing([], null, prontuario, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(prontuario);
      });

      it('should return initial array if no Prontuario is added', () => {
        const prontuarioCollection: IProntuario[] = [sampleWithRequiredData];
        expectedResult = service.addProntuarioToCollectionIfMissing(prontuarioCollection, undefined, null);
        expect(expectedResult).toEqual(prontuarioCollection);
      });
    });

    describe('compareProntuario', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProntuario(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProntuario(entity1, entity2);
        const compareResult2 = service.compareProntuario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProntuario(entity1, entity2);
        const compareResult2 = service.compareProntuario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProntuario(entity1, entity2);
        const compareResult2 = service.compareProntuario(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
