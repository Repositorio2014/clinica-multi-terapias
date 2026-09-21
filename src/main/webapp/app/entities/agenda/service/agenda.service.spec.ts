import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAgenda } from '../agenda.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../agenda.test-samples';

import { AgendaService, RestAgenda } from './agenda.service';

const requireRestSample: RestAgenda = {
  ...sampleWithRequiredData,
  dataHoraInicio: sampleWithRequiredData.dataHoraInicio?.toJSON(),
  dataHoraFim: sampleWithRequiredData.dataHoraFim?.toJSON(),
};

describe('Agenda Service', () => {
  let service: AgendaService;
  let httpMock: HttpTestingController;
  let expectedResult: IAgenda | IAgenda[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AgendaService);
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

    it('should create a Agenda', () => {
      const agenda = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(agenda).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Agenda', () => {
      const agenda = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(agenda).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Agenda', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Agenda', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Agenda', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAgendaToCollectionIfMissing', () => {
      it('should add a Agenda to an empty array', () => {
        const agenda: IAgenda = sampleWithRequiredData;
        expectedResult = service.addAgendaToCollectionIfMissing([], agenda);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agenda);
      });

      it('should not add a Agenda to an array that contains it', () => {
        const agenda: IAgenda = sampleWithRequiredData;
        const agendaCollection: IAgenda[] = [
          {
            ...agenda,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAgendaToCollectionIfMissing(agendaCollection, agenda);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Agenda to an array that doesn't contain it", () => {
        const agenda: IAgenda = sampleWithRequiredData;
        const agendaCollection: IAgenda[] = [sampleWithPartialData];
        expectedResult = service.addAgendaToCollectionIfMissing(agendaCollection, agenda);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agenda);
      });

      it('should add only unique Agenda to an array', () => {
        const agendaArray: IAgenda[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const agendaCollection: IAgenda[] = [sampleWithRequiredData];
        expectedResult = service.addAgendaToCollectionIfMissing(agendaCollection, ...agendaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const agenda: IAgenda = sampleWithRequiredData;
        const agenda2: IAgenda = sampleWithPartialData;
        expectedResult = service.addAgendaToCollectionIfMissing([], agenda, agenda2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agenda);
        expect(expectedResult).toContain(agenda2);
      });

      it('should accept null and undefined values', () => {
        const agenda: IAgenda = sampleWithRequiredData;
        expectedResult = service.addAgendaToCollectionIfMissing([], null, agenda, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agenda);
      });

      it('should return initial array if no Agenda is added', () => {
        const agendaCollection: IAgenda[] = [sampleWithRequiredData];
        expectedResult = service.addAgendaToCollectionIfMissing(agendaCollection, undefined, null);
        expect(expectedResult).toEqual(agendaCollection);
      });
    });

    describe('compareAgenda', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAgenda(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAgenda(entity1, entity2);
        const compareResult2 = service.compareAgenda(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAgenda(entity1, entity2);
        const compareResult2 = service.compareAgenda(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAgenda(entity1, entity2);
        const compareResult2 = service.compareAgenda(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
